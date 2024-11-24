package com.spring.demo.entity;
//ADD COLUMN Pr_description TEXT,
//ADD COLUMN Pr_hashtags VARCHAR(255); 추가
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
    @Column(name = "Pr_num") // 연습실 번호
    private Integer prNum;

    @Column(name = "Pr_address") // 연습실 주소
    private String prAddress;

    @Column(name = "Pr_warnings") // 연습실 주의사항
    private String prWarnings;

    @Column(name = "Pr_parking") // 주차 가능 여부
    private String prParking;

    @Column(name = "Pr_max_person") // 최대 수용 인원
    private Integer prMaxPerson;

    @Column(name = "Pr_price") // 연습실 가격
    private Integer prPrice;

    @Column(name = "Pr_space_type") // 공간 유형 (0: 전체, 1: 연습실, 2: 밴드 연습실, 3: 댄스 연습실, 4: 음악 연습실)
    private Integer prSpaceType;

    @Column(name = "pr_name")
    private String prName;
    public PrDetail(Integer prNum, String prAddress, String prWarnings, String prParking, Integer prMaxPerson, Integer prPrice, Integer prSpaceType) {
        this.prNum = prNum;
        this.prAddress = prAddress;
        this.prWarnings = prWarnings;
        this.prParking = prParking;
        this.prMaxPerson = prMaxPerson;
        this.prPrice = prPrice;
        this.prSpaceType = prSpaceType;
    }
}
