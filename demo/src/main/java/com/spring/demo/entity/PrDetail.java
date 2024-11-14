package com.spring.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pr_detail")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PrDetail {
    @Id
    @Column(name = "Pr_num") //연습실번호
    private Integer prNum;

    @Column(name = "Pr_address") //연습실주소
    private String prAddress;

    @Column(name = "Pr_warnings") //연습실 주의사항
    private String prWarnings;

    @Column(name = "Pr_parking") //주차 가능여부
    private String prParking;

    @Column(name = "Pr_max_person") //연습실 최대 수용 인원
    private Integer prMaxPerson;

    @Column(name = "Pr_price") //연습실 가격
    private Integer prPrice;

    public PrDetail(Integer prNum, String prAddress, String prWarnings, String prParking, Integer prMaxPerson, Integer prPrice) {
        this.prNum = prNum;
        this.prAddress = prAddress;
        this.prWarnings = prWarnings;
        this.prParking = prParking;
        this.prMaxPerson = prMaxPerson;
        this.prPrice = prPrice;
    }
}
