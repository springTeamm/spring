package com.spring.demo.admin.userManager.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "pr_booking")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PrBooking {
    @Id
    @GeneratedValue
    @Column(name = "Booking_num") //예약번호
    private Integer bookingNum;

    @Column(name = "Pr_num") //연습실 번호
    private Integer prNum;

    @Column(name = "User_num") //유저 번호
    private Integer userNum;

    @Column(name = "Booking_date")
    private LocalDateTime bookingDate;

    @Column(name = "Booking_total_person") // 예약 총인원
    private Integer bookingTotalPerson;

    @Column(name = "Booking_total_price") // 예약 총 가격
    private Integer bookingTotalPrice;

    @Column(name = "Booking_using_time") //사용 시간
    private Integer bookingUsingTime;

    @Column(name = "Booking_payment_method") //결제방법
    private String bookingPaymentMethod;


    public PrBooking(Integer bookingNum, Integer prNum, Integer userNum, LocalDateTime bookingDate, Integer bookingTotalPerson, Integer bookingTotalPrice, Integer bookingUsingTime, String bookingPaymentMethod) {
        this.bookingNum = bookingNum;
        this.prNum = prNum;
        this.userNum = userNum;
        this.bookingDate = bookingDate;
        this.bookingTotalPerson = bookingTotalPerson;
        this.bookingTotalPrice = bookingTotalPrice;
        this.bookingUsingTime = bookingUsingTime;
        this.bookingPaymentMethod = bookingPaymentMethod;
    }
}