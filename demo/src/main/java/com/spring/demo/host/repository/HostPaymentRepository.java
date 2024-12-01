package com.spring.demo.host.repository;

import com.spring.demo.entity.Payment;
import com.spring.demo.entity.PracticeRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface HostPaymentRepository extends JpaRepository<Payment,Integer> {

    Optional<Payment> findByBookingNum(Integer bookingNum);
    @Query(value = """
        SELECT p.pay_num AS payNum,
               p.booking_num AS bookingNum,
               p.pay_price AS payPrice,
               p.pay_date AS payDate,
               p.booking_cancel AS bookingCancel,
               b.booking_date AS bookingDate,
               b.user_num AS userNum,
               b.pr_num AS prNum,
               r.re_date AS refundDate,
               r.re_price AS refundPrice,
               pr.pr_name AS prName,
               pr.location_name AS locationName,
               u.user_name AS userName
        FROM payment p
        LEFT JOIN pr_booking b ON p.booking_num = b.booking_num
        LEFT JOIN refund r ON p.pay_num = r.pay_num
        LEFT JOIN practice_room pr ON b.pr_num = pr.pr_num
        LEFT JOIN user u ON b.user_num = u.user_num
        """, nativeQuery = true)
    List<Object[]> findAllWithDetails();

    List<Payment> findAll();


}
