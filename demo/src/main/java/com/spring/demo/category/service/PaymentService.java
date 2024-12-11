package com.spring.demo.category.service;

import com.spring.demo.category.repository.PaymentRepository;
import com.spring.demo.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // 결제 저장
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    // 예약 ID로 결제 내역 조회
    public List<Payment> getPaymentsByBookingId(Integer bookingId) {
        return paymentRepository.findByBookingNum(bookingId);
    }

    // 결제 상태 업데이트
    public Payment updatePaymentStatus(Integer paymentId, String status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("결제 정보를 찾을 수 없습니다. ID: " + paymentId));
        payment.setPayStatus(status);
        return paymentRepository.save(payment);
    }

    // Toss 결제 처리
    public String processTossPayment(Payment payment) {
        RestTemplate restTemplate = new RestTemplate();

        // Toss Payments API URL
        String tossApiUrl = "https://api.tosspayments.com/v1/payments";
        String tossSecretKey = "API키"; // Toss Payments Secret Key (테스트용)

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(tossSecretKey, ""); // Basic Auth 설정 (Secret Key 사용)

        // 요청 바디 설정
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("orderId", "order-" + payment.getPayNum()); // 고유 주문 ID
        requestBody.put("amount", payment.getPayPrice()); // 결제 금액
        requestBody.put("orderName", "연습실 예약 결제"); // 결제명
        requestBody.put("successUrl", "http://localhost:8000/success"); // 결제 성공 시 리다이렉트 URL
        requestBody.put("failUrl", "http://localhost:8000/fail"); // 결제 실패 시 리다이렉트 URL

        // 요청 생성
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            // Toss 호출
            ResponseEntity<String> response = restTemplate.postForEntity(tossApiUrl, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                // 결제 성공 시 응답 데이터 반환
                payment.setPayStatus("CONFIRMED");
                paymentRepository.save(payment); // 결제 상태 저장
                return response.getBody();
            } else {
                // 결제 실패 시 예외 처리
                payment.setPayStatus("FAILED");
                paymentRepository.save(payment);
                throw new RuntimeException("Toss Payments 결제 실패: " + response.getBody());
            }
        } catch (Exception e) {
            // Toss API 호출 실패 처리
            payment.setPayStatus("FAILED");
            paymentRepository.save(payment);
            throw new RuntimeException("Toss Payments API 호출 중 오류 발생: " + e.getMessage());
        }
    }
}