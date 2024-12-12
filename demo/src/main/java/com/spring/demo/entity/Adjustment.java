package com.spring.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "adjustment")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Adjustment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Ad_num") //정산번호
    private Integer adNum;

    @Column(name = "Pay_num") //결제번호
    private Integer payNum;

    @Column(name = "Ad_price") //정산금액
    private Integer adPrice;

    @Column(name = "Ad_Date") //정산일
    private Date adDate;

    public Adjustment(Integer adNum, Integer payNum, Integer adPrice, Date adDate) {
        this.adNum = adNum;
        this.payNum = payNum;
        this.adPrice = adPrice;
        this.adDate = adDate;
    }
}
