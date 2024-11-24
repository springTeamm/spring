package com.spring.demo.category.repository;

import com.spring.demo.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByBookingNum(Integer bookingNum);
}
