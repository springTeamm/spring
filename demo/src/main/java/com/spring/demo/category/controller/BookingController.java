package com.spring.demo.category.controller;

import com.spring.demo.category.service.PaymentService;
import com.spring.demo.category.service.PrBookingService;
import com.spring.demo.entity.Payment;
import com.spring.demo.entity.PrBooking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
@RestController
@RequestMapping("/booking")
public class BookingController {

    private final PrBookingService prBookingService;
    private final PaymentService paymentService;

    @Autowired
    public BookingController(PrBookingService prBookingService, PaymentService paymentService) {
        this.prBookingService = prBookingService;
        this.paymentService = paymentService;
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveAndPay(@RequestBody PrBooking bookingRequest) {
        if (bookingRequest.getPrNum() == null) {
            return ResponseEntity.badRequest().body("연습실 번호(prNum)는 필수입니다.");
        }

        try {
            // 예약 저장
            PrBooking savedBooking = prBookingService.saveBooking(bookingRequest);

            // 결제 처리
            Payment payment = new Payment();
            payment.setBookingNum(savedBooking.getBookingNum());
            payment.setPayPrice(savedBooking.getBookingTotalPrice());
            payment.setPayDate(new Date());
            payment.setPayStatus("PENDING");

            // Toss 연동
            String tossPaymentResponse = paymentService.processTossPayment(payment);

            return ResponseEntity.ok("예약이 성공적으로 처리되었습니다.\n" +
                    "방 번호: " + savedBooking.getPrNum() +
                    "\n예약 번호: " + savedBooking.getBookingNum() +
                    "\n결제 세부사항: " + tossPaymentResponse);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("오류 발생: " + e.getMessage());
        }
    }
}
