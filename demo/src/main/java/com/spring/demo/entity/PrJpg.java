package com.spring.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pr_jpg")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PrJpg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Pr_jpg_num") // 기본 키
    private Integer prJpgNum;

    @Column(name = "Pr_num", nullable = false) // 외래 키
    private Integer prNum;

    @Column(name = "Pr_jpg_origin_name") // 원본 파일 이름
    private String prJpgOriginName;

    @Column(name = "Pr_jpg_path") // 파일 경로
    private String prJpgPath;

    @Column(name = "Pr_jpg_name") // 저장된 파일 이름
    private String prJpgName;

    public PrJpg(Integer prNum, String prJpgOriginName, String prJpgPath, String prJpgName) {
        this.prNum = prNum;
        this.prJpgOriginName = prJpgOriginName;
        this.prJpgPath = prJpgPath;
        this.prJpgName = prJpgName;
    }
}
