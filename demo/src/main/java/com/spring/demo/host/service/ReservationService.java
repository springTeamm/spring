package com.spring.demo.host.service;





import com.spring.demo.entity.*;
import com.spring.demo.host.DTO.ReservationDTO;
import com.spring.demo.host.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final HostPaymentRepository paymentRepository;
    private final HostPrBookingRepository prBookingRepository;
    private final HostPracticeRoomRepository practiceRoomRepository;
    private final HostUserRepository userRepository;
    private final HostPrBookingRepository hostPrBookingRepository;
    private final HostRefund hostRefundRepository;
    private final HostAdjustmentRepository hostAdjustmentRepository;
    private final ReviewRepository reviewRepository;
    private final HostPrDetailRepository hostPrDetailRepository;

    @Autowired
    public ReservationService(HostPaymentRepository paymentRepository, HostPrBookingRepository prBookingRepository,
                              HostPracticeRoomRepository practiceRoomRepository, HostUserRepository userRepository, HostPrBookingRepository hostPrBookingRepository, HostRefund hostRefundRepository
    , HostAdjustmentRepository hostAdjustmentRepository, ReviewRepository reviewRepository, HostPrDetailRepository hostPrDetailRepository) {
        this.paymentRepository = paymentRepository;
        this.prBookingRepository = prBookingRepository;
        this.practiceRoomRepository = practiceRoomRepository;
        this.userRepository = userRepository;
        this.hostPrBookingRepository = hostPrBookingRepository;
        this.hostRefundRepository = hostRefundRepository;
        this.hostAdjustmentRepository = hostAdjustmentRepository;
        this.reviewRepository = reviewRepository;
        this.hostPrDetailRepository = hostPrDetailRepository;
    }

    public List<ReservationDTO> getReservationsForAuthenticatedUser() {
        // 현재 인증된 사용자의 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("사용자가 인증되지 않았습니다.");
        }

        // Spring Security의 UserDetails에서 사용자 ID를 가져옴
        Object principal = authentication.getPrincipal();
        String userId;
        if (principal instanceof UserDetails) {
            userId = ((UserDetails) principal).getUsername();
        } else {
            userId = principal.toString();
        }

        // 사용자 ID로 User 엔티티 조회
        User authenticatedUser = userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // 해당 사용자의 예약 정보 조회
        return paymentRepository.findAll().stream()
                .filter(payment -> {
                    PrBooking booking = prBookingRepository.findById(payment.getBookingNum())
                            .orElseThrow(() -> new RuntimeException("Booking not found"));
                    return booking.getUserNum().equals(authenticatedUser.getUserNum());
                })
                .map(payment -> {
                    PrBooking booking = prBookingRepository.findById(payment.getBookingNum())
                            .orElseThrow(() -> new RuntimeException("Booking not found"));
                    PracticeRoom room = practiceRoomRepository.findById(booking.getPrNum())
                            .orElseThrow(() -> new RuntimeException("Practice room not found"));
                    PrDetail prDetail = hostPrDetailRepository.findById(booking.getPrNum())
                            .orElseThrow(() -> new RuntimeException("Practice room detail not found"));

                    return new ReservationDTO(
                            booking.getBookingNum(),
                            room.getPrNum(),
                            payment.getPayNum(),
                            authenticatedUser.getUserName(), // 현재 인증된 사용자
                            booking.getBookingDate(),
                            room.getPrName(),
                            payment.getPayDate(),
                            payment.getPayPrice(),
                            booking.getBookingTotalPerson(),
                            room.getLocationName(),
                            authenticatedUser.getUserPhone(),
                            room.getPrUseable()
                    );
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteBookingsByIds(List<Integer> bookingNums) {
        for (Integer bookingNum : bookingNums) {
            // 1. Payment 조회
            Payment payment = paymentRepository.findByBookingNum(bookingNum)
                    .orElseThrow(() -> new RuntimeException("Payment not found for bookingNum: " + bookingNum));

            // 2. Refund 데이터 생성 및 저장 (Payment 삭제 전에 생성)
            Refund refund = new Refund();
            refund.setPayNum(payment.getPayNum());
            refund.setRePrice(payment.getPayPrice());
            refund.setReDate(new Date()); // 현재 날짜로 설정
            hostRefundRepository.save(refund);

            // 3. Refund 관련 데이터 삭제
            hostRefundRepository.deleteByPayNum(payment.getPayNum());

            // 4. Adjustment 데이터 삭제
            hostAdjustmentRepository.deleteByPayNum(payment.getPayNum());

            // 5. Payment 데이터 삭제
            paymentRepository.delete(payment);

            // 7. Review 데이터 삭제
            reviewRepository.deleteByBookingNum(bookingNum);
            // 6. PrBooking 데이터 삭제
            prBookingRepository.deleteById(bookingNum);

        }

    }


}
