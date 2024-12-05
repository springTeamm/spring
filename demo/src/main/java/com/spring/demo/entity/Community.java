package com.spring.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
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
    private LocalDateTime date;

    @Column(name = "C_schedule") // 일정
    private LocalDateTime schedule;

    @Column(name = "C_location") // 위치
    private String location;

    @Column(name = "C_members") // 멤버 이름들
    private String members;

    @Column(name = "C_links") // 그룹셋 리스트 링크
    private String links;


    @OneToMany(mappedBy = "community", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityPhoto> photos; // 즉시 로딩


    public Community(Integer cNum, String title, String text, Integer userNum, LocalDateTime date, String location, String members, String links, Integer categoryNum, List<CommunityPhoto> photos) {
        this.cNum = cNum;
        this.title = title;
        this.text = text;
        this.userNum = userNum;
        this.date = date;
        this.location = location;
        this.members = members;
        this.links = links;
        this.categoryNum = categoryNum;
        this.photos = photos;
    }
}
