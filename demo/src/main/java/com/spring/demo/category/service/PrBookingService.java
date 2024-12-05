package com.spring.demo.category.service;

import com.spring.demo.category.repository.PrBookingRepository;
import com.spring.demo.entity.PrBooking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
@Transactional
public class PrBookingService {

    private final PrBookingRepository prBookingRepository;

    @Autowired
    public PrBookingService(PrBookingRepository prBookingRepository) {
        this.prBookingRepository = prBookingRepository;
    }

    // 예약 저장 (시간 중복 확인 포함)
    public PrBooking saveBooking(PrBooking booking) {
        validateBookingTime(booking.getPrNum(), booking.getBookingDate(), booking.getBookingUsingTime());
        return prBookingRepository.save(booking);
    }

    // 특정 날짜, 연습실 예약 조회
    public List<PrBooking> getBookingsByRoomAndDate(Integer prNum, String date) {
        return prBookingRepository.findByPrNumAndBookingDate(prNum, parseDate(date));
    }

    // 특정 연습실의 전체 예약 조회
    public List<PrBooking> getBookingsByRoom(Integer prNum) {
        return prBookingRepository.findByPrNum(prNum);
    }

    // 예약 취소
    public void cancelBooking(Integer bookingNum) {
        PrBooking booking = prBookingRepository.findById(bookingNum)
                .orElseThrow(() -> new RuntimeException("예약 정보를 찾을 수 없습니다."));
        prBookingRepository.delete(booking);
    }

    // 예약 시간 중복 검증
    private void validateBookingTime(Integer prNum, Date bookingDate, Integer usingTime) {
        List<PrBooking> existingBookings = prBookingRepository.findByPrNumAndBookingDate(prNum, bookingDate);

        long requestedStartMillis = bookingDate.getTime();
        long requestedEndMillis = requestedStartMillis + usingTime * 60 * 60 * 1000;

        for (PrBooking existingBooking : existingBookings) {
            long existingStartMillis = existingBooking.getBookingDate().getTime();
            long existingEndMillis = existingStartMillis + existingBooking.getBookingUsingTime() * 60 * 60 * 1000;

            if (requestedStartMillis < existingEndMillis && requestedEndMillis > existingStartMillis) {
                throw new RuntimeException("예약 시간이 겹칩니다. 다른 시간을 선택해주세요.");
            }
        }
    }

    // String -> Date 변환
    private Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (Exception e) {
            throw new RuntimeException("날짜 형식이 올바르지 않습니다: " + date);
        }
    }
}
