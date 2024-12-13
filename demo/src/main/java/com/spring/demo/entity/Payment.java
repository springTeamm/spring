package com.spring.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Pay_num") //결제 번호
    private Integer payNum;

    @Column(name = "Booking_num") //예약번호
    private Integer bookingNum;

    @Column(name = "Pay_price") //결제 금액
    private Integer payPrice;

    @Column(name = "Pay_date") //결제 날짜
    private Date payDate;

    @Column(name = "Pay_status") //결제 상태
    private String payStatus;

    @Column(name = "Booking_cancel")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bookingCancel;
    public Payment(Integer payNum, Integer bookingNum, Integer payPrice, Date payDate, String payStatus, Date bookingCancel) {
        this.payNum = payNum;
        this.bookingNum = bookingNum;
        this.payPrice = payPrice;
        this.payDate = payDate;
        this.payStatus = payStatus;
        this.bookingCancel = bookingCancel;
    }


}
