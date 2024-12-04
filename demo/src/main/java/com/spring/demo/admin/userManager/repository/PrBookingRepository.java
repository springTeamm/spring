package com.spring.demo.admin.userManager.repository;

import com.spring.demo.admin.userManager.entity.PrBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrBookingRepository extends JpaRepository<PrBooking, Integer> {
    List<PrBooking> findByUserNum(Integer userNum);

    List<PrBooking> findByUserNumOrderByBookingDate(Integer userNum);
}
