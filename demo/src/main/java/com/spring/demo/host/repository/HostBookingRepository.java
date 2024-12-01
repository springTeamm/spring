package com.spring.demo.host.repository;

import com.spring.demo.entity.PrBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostBookingRepository extends JpaRepository<PrBooking,Integer> {
    Optional<PrBooking> findByBookingNum(Integer bookingNum);
}
