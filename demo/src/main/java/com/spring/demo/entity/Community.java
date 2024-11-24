package com.spring.demo.entity;

import jakarta.persistence.*;
import lombok.*;
//ADD COLUMN C_schedule DATETIME NULL,
//ADD COLUMN C_location VARCHAR(255) NULL,
//ADD COLUMN C_members VARCHAR(255) NULL,
//ADD COLUMN C_links VARCHAR(255) NULL; 추가
import java.util.Date;
@Entity
@Table(name = "community")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Community {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C_num") // 커뮤니티 번호
    private Integer cNum;

    @Column(name = "User_num") // 유저 번호
    private Integer userNum;

    @Column(name = "C_category_num") // 커뮤니티 카테고리 번호
    private Integer categoryNum;

    @Column(name = "C_title") // 커뮤니티 제목
    private String title;

    @Column(name = "C_text") // 커뮤니티 내용
    private String text;

    @Column(name = "C_date") // 생성 날짜
    private Date date;

    @Column(name = "C_schedule") // 일정
    private Date schedule;

    @Column(name = "C_location") // 위치
    private String location;

    @Column(name = "C_members") // 멤버 이름들
    private String members;

    @Column(name = "C_links") // 그룹셋 리스트 링크
    private String links;

    public Community(Integer cNum, Integer userNum, Integer categoryNum, String title, String text, Date date, Date schedule, String location, String members, String links) {
        this.cNum = cNum;
        this.userNum = userNum;
        this.categoryNum = categoryNum;
        this.title = title;
        this.text = text;
        this.date = date;
        this.schedule = schedule;
        this.location = location;
        this.members = members;
        this.links = links;
    }
}
