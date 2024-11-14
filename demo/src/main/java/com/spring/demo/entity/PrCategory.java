package com.spring.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pr_category")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PrCategory {
    @Id
    @GeneratedValue
    @Column(name = "Category_num") //카테고리번호
    private int categoryNum;

    @Column(name = "Category_name")// 카테고리명
    private String categoryName;

    public PrCategory(int categoryNum, String categoryName) {
        this.categoryNum = categoryNum;
        this.categoryName = categoryName;
    }
}
