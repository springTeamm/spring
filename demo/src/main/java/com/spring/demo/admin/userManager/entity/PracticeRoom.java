package com.spring.demo.admin.userManager.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "practice_room")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PracticeRoom {
    @Id
    @GeneratedValue
    @Column(name = "Pr_num") // 연습실 번호
    private Integer prNum;

    @Column(name = "Host_info_num") // 사업장 번호
    private Integer hostInfoNum;

    @Column(name = "Pr_name") //연습실 이름
    private String prName;

    @Column(name = "Pr_useable") //연습실 사용 가능여부
    private String prUseable;
    @Column(name = "Location_name") // 장소 이름
    private String locationName;
    @Column(name = "Category_num") //카테고리 번호
    private Integer categoryNum;

    public PracticeRoom(Integer prNum, Integer hostInfoNum, String prName, String prUseable, Integer categoryNum) {
        this.prNum = prNum;
        this.hostInfoNum = hostInfoNum;
        this.prName = prName;
        this.prUseable = prUseable;
        this.categoryNum = categoryNum;
    }
}
