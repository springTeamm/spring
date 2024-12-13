package com.spring.demo.host.service;

import com.spring.demo.category.repository.PrBookingRepository;
import com.spring.demo.entity.*;
import com.spring.demo.host.DTO.ReservationCancellationDTO;
import com.spring.demo.host.DTO.RoomDetailsDTO;
import com.spring.demo.host.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HostCancellService {

    @Autowired
    private HostPracticeRoomRepository hostPracticeRoomRepository;
    @Autowired
    private HostPaymentRepository hostpaymentRepository;
    @Autowired
    private HostPaymentDateRepository hostPaymentDateRepository;
    @Autowired
    private HostRefund hostRefund;
    @Autowired
    private HostPrBookingRepository hostPrBookingRepository;
    @Autowired
    private HostUserRepository hostUserRepository;
    @Autowired
    private PrBookingRepository prBookingRepository;
    @Autowired
    private HostPaymentRepository hostPaymentRepository;
    public List<ReservationCancellationDTO> getAllCancellations() {

        List<Object[]> rawResults = hostpaymentRepository.findAllWithDetails();


        List<ReservationCancellationDTO> cancellations = new ArrayList<>();
        for (Object[] result : rawResults) {
            ReservationCancellationDTO dto = new ReservationCancellationDTO();
            dto.setPayNum((Integer) result[0]);
            dto.setBookingNum((Integer) result[1]);
            dto.setPayPrice((Integer) result[2]);
            dto.setPayDate((Date) result[3]);
            dto.setBookingCancel((Date) result[4]);
            dto.setBookingDate((Date) result[5]);
            dto.setUserNum((Integer) result[6]);
            dto.setPrNum((Integer) result[7]);
            dto.setRefundDate((Date) result[8]);
            dto.setRefundPrice((Integer) result[9]);
            dto.setPrName((String) result[10]);
            dto.setLocationName((String) result[11]);
            dto.setUserName((String) result[12]);

            cancellations.add(dto);
        }

        return cancellations;
    }
    @Transactional
    public void approveCancellations(List<Integer> payNums, Date refundDate) {
        for (Integer payNum : payNums) {
            // Payment 엔티티 업데이트
            Payment payment = hostPaymentRepository.findById(payNum)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid payment ID: " + payNum));
            payment.setBookingCancel(new Date()); // 현재 날짜로 설정
            hostPaymentRepository.save(payment);

            // Refund 엔티티 생성 및 저장
            Refund refund = new Refund();
            refund.setPayNum(payNum);
            refund.setRePrice(payment.getPayPrice());
            refund.setReDate(refundDate); // 전달받은 날짜 설정
            hostRefund.save(refund);
        }
    }

}
