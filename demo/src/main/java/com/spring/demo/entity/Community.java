package com.spring.demo.entity;

import jakarta.persistence.*;
import lombok.*;

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
    @GeneratedValue
    @Column(name = "C_num") //커뮤니티 번호
    private Integer cNum;

    @Column(name = "User_num") //유저 번호
    private Integer userNum;

    @Column(name = "C_category_num") //커뮤니티 카테고리 번호
    private Integer categoryNum;

    @Column(name = "C_title") //커뮤니티 제목
    private String title;

    @Column(name = "C_text") //커뮤니티 내용
    private String text;

    @Column(name = "C_making_date") //커뮤니티 생성 날짜
    private Date makingDate;

    public Community(Integer cNum, Integer userNum, Integer categoryNum, String title, String text, Date makingDate) {
        this.cNum = cNum;
        this.userNum = userNum;
        this.categoryNum = categoryNum;
        this.title = title;
        this.text = text;
        this.makingDate = makingDate;
    }
}
