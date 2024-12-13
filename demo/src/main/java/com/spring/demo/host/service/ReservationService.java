package com.spring.demo.host.service;





import com.spring.demo.entity.*;
import com.spring.demo.host.DTO.ReservationDTO;
import com.spring.demo.host.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<ReservationDTO> getReservations() {
        return paymentRepository.findAll().stream().map(payment -> {
            PrBooking booking = prBookingRepository.findById(payment.getBookingNum())
                    .orElseThrow(() -> new RuntimeException("Booking not found"));
            PracticeRoom room = practiceRoomRepository.findById(booking.getPrNum())
                    .orElseThrow(() -> new RuntimeException("Practice room not found"));
            User user = userRepository.findById(booking.getUserNum())
                    .orElseThrow(() -> new RuntimeException("User not found"));


            return new ReservationDTO(
                    booking.getBookingNum(), // bookingNum 추가
                    room.getPrNum(),
                    payment.getPayNum(),
                    user.getUserName(),
                    booking.getBookingDate(),
                    room.getPrName(),
                    payment.getPayDate(),
                    payment.getPayPrice(),
                    booking.getBookingTotalPerson(),
                    room.getLocationName(),
                    user.getUserPhone(),
                    room.getPrUseable()

            );
        }).collect(Collectors.toList());
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

    @Transactional
    public PrBooking addBookingWithPayment(PrBooking bookingData, Integer payPrice, String payStatus) {
        // 1. PrBooking 저장
        PrBooking savedBooking = prBookingRepository.save(bookingData);

        // 2. Payment 생성 및 저장
        Payment payment = new Payment();
        payment.setBookingNum(savedBooking.getBookingNum()); // PrBooking의 예약 번호 참조
        payment.setPayPrice(payPrice); // 전달받은 결제 금액
        payment.setPayDate(new Date()); // 현재 시간
        payment.setPayStatus(payStatus); // 전달받은 결제 상태
        paymentRepository.save(payment);

        return savedBooking;
    }


}
