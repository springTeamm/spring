package com.spring.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "refund")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Refund {
    @Id
    @GeneratedValue
    @Column(name = "Re_num") //환불번호
    private Integer reNum;

    @Column(name = "Pay_num") //결제번호
    private Integer payNum;

    @Column(name = "Re_price") // 환불가격
    private Integer rePrice;

    @Column(name = "Re_date") //환불날짜
    private Date reDate;

    public Refund(Integer reNum, Integer payNum, Integer rePrice, Date reDate) {
        this.reNum = reNum;
        this.payNum = payNum;
        this.rePrice = rePrice;
        this.reDate = reDate;
    }
}
