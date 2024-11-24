package com.spring.demo.category.service;

import com.spring.demo.category.repository.PrBookingRepository;
import com.spring.demo.category.repository.PaymentRepository;
import com.spring.demo.entity.Payment;
import com.spring.demo.entity.PrBooking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PrBookingService {

    private final PrBookingRepository prBookingRepository;
    private final PaymentRepository paymentRepository;

    @Autowired
    public PrBookingService(PrBookingRepository prBookingRepository, PaymentRepository paymentRepository) {
        this.prBookingRepository = prBookingRepository;
        this.paymentRepository = paymentRepository;
    }

    // 예약 저장
    public PrBooking saveBooking(PrBooking booking) {
        if (booking.getPrNum() == null) {
            throw new RuntimeException("연습실을 선택해주세요.");
        }

        // 예약 시간 중복 확인
        validateBookingTime(booking.getPrNum(), booking.getBookingDate(), booking.getBookingUsingTime());

        return prBookingRepository.save(booking);
    }

    // 예약 수정
    public PrBooking updateBooking(Integer bookingId, PrBooking updatedBooking) {
        PrBooking existingBooking = prBookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("예약 정보를 찾을 수 없습니다. ID: " + bookingId));

        validateBookingTime(updatedBooking.getPrNum(), updatedBooking.getBookingDate(), updatedBooking.getBookingUsingTime(), bookingId);

        // 예약정보 업데이트
        existingBooking.setBookingTotalPerson(updatedBooking.getBookingTotalPerson());
        existingBooking.setBookingDate(updatedBooking.getBookingDate());
        existingBooking.setBookingUsingTime(updatedBooking.getBookingUsingTime());

        return prBookingRepository.save(existingBooking);
    }

    // 방의 예약 목록 조회 - 호스트에서 쓸 수 있음
    public List<PrBooking> getBookingsByRoom(Integer prNum) {
        return prBookingRepository.findByPrNum(prNum);
    }

    // 예약 취소 - 사용자
    public double cancelBookingByUser(Integer bookingId) {
        PrBooking booking = prBookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("예약 정보를 찾을 수 없습니다. ID: " + bookingId));

        List<Payment> payments = paymentRepository.findByBookingNum(bookingId);
        if (payments.isEmpty()) {
            throw new RuntimeException("결제 정보가 없습니다. 예약 ID: " + bookingId);
        }

        Payment payment = payments.get(0);
        long daysUntilBooking = calculateDaysUntilBooking(booking.getBookingDate());
        double refundAmount;

        if (daysUntilBooking >= 3) {
            refundAmount = payment.getPayPrice();
        } else if (daysUntilBooking == 2) {
            refundAmount = payment.getPayPrice() * 0.7;
        } else if (daysUntilBooking == 1) {
            refundAmount = payment.getPayPrice() * 0.5;
        } else {
            throw new RuntimeException("당일 예약 취소는 불가능합니다.");
        }

        payment.setPayPrice((int) refundAmount);
        payment.setPayStatus("CANCELLED");
        paymentRepository.save(payment);

        prBookingRepository.deleteById(bookingId);

        return refundAmount;
    }

    // 예약 취소 - 호스트
    public double cancelBookingByHost(Integer bookingId, String reason) {
        PrBooking booking = prBookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("예약 정보를 찾을 수 없습니다. ID: " + bookingId));

        List<Payment> payments = paymentRepository.findByBookingNum(bookingId);
        if (payments.isEmpty()) {
            throw new RuntimeException("결제 정보가 없습니다. 예약 ID: " + bookingId);
        }

        Payment payment = payments.get(0);
        double refundAmount = payment.getPayPrice();
        payment.setPayPrice((int) refundAmount);
        payment.setPayStatus("CANCELLED");
        paymentRepository.save(payment);

        prBookingRepository.deleteById(bookingId);

        System.out.println("호스트 취소 사유: " + reason);

        return refundAmount;
    }

    // 예약 시간 계산 (중복떄문에)
    private long calculateDaysUntilBooking(Date bookingDate) {
        long diffInMillies = bookingDate.getTime() - new Date().getTime();
        return diffInMillies / (24 * 60 * 60 * 1000);
    }

    // 예약 시간 중복 확인
    private void validateBookingTime(Integer prNum, Date bookingDate, Integer usingTime, Integer excludeBookingId) {
        List<PrBooking> existingBookings = prBookingRepository.findByPrNumAndBookingDate(prNum, bookingDate);

        long requestedStartMillis = bookingDate.getTime();
        long requestedEndMillis = requestedStartMillis + usingTime * 60 * 60 * 1000;

        for (PrBooking existingBooking : existingBookings) {
            if (excludeBookingId != null && existingBooking.getBookingNum().equals(excludeBookingId)) {
                continue;
            }

            long existingStartMillis = existingBooking.getBookingDate().getTime();
            long existingEndMillis = existingStartMillis + existingBooking.getBookingUsingTime() * 60 * 60 * 1000;

            if (requestedStartMillis < existingEndMillis && requestedEndMillis > existingStartMillis) {
                throw new RuntimeException("예약 시간이 겹칩니다. 다른 시간을 선택해주세요.");
            }
        }
    }

    private void validateBookingTime(Integer prNum, Date bookingDate, Integer usingTime) {
        validateBookingTime(prNum, bookingDate, usingTime, null);
    }
}
