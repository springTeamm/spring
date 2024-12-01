package com.spring.demo.host.repository;

import com.spring.demo.entity.PrReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<PrReview, Integer> {
    Optional<PrReview> deleteByBookingNum(Integer bookingNum);
}