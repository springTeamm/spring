package com.spring.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "community_category")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CommunityCategory {
    @Id
    @GeneratedValue
    @Column(name = "Co_category_num") //커뮤니티 카테고리 코드
    private Integer coCategoryNum;

    @Column(name = "Co_category_name") //커뮤니티 카테고리 이름
    private String coCategoryName;

    public CommunityCategory(Integer coCategoryNum, String coCategoryName) {
        this.coCategoryNum = coCategoryNum;
        this.coCategoryName = coCategoryName;
    }
}
