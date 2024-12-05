package com.spring.demo.entity;
//ADD COLUMN Pr_description TEXT,
//ADD COLUMN Pr_hashtags VARCHAR(255); 추가
import com.spring.demo.security.entity.HostInfo;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "pr_detail")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PrDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Pr_num")
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

    @Column(name = "pr_open_time") // 영업 시작 시간
    private LocalTime prOpenTime;

    @Column(name = "pr_close_time") // 영업 종료 시간
    private LocalTime prCloseTime;

    @Column(name = "pr_description", columnDefinition = "TEXT") // 연습실 설명
    private String prDescription;
    @Column(name = "pr_name")
    private String prName;
    @Column(name = "Host_num") // HostInfo와의 연결 (FK)
    private Integer hostNum;

    @Column(name = "Location_name") // 장소명
    private String locationName;
    @ManyToOne
    @JoinColumn(name = "Host_num", referencedColumnName = "Host_num", insertable = false, updatable = false)
    private HostInfo hostInfo;

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
