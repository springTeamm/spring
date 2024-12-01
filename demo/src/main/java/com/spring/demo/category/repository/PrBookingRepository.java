package com.spring.demo.category.repository;

import com.spring.demo.entity.PrBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrBookingRepository extends JpaRepository<PrBooking, Integer> {
    List<PrBooking> findByPrNumAndBookingDate(Integer prNum, Date bookingDate);

    List<PrBooking> findByPrNum(Integer prNum);

    Optional<PrBooking> findByBookingNum(Integer bookingNum);
}
