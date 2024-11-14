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
public class RoomPhoto {
    @Id
    @GeneratedValue
    @Column(name = "Pr_jpg_num") //연습실 사진번호
    private Integer jpgNum;

    @Column(name = "Pr_num") //연습실 번호
    private Integer prNum;

    @Column(name = "Pr_jpg_origin_name") //연습실 사진 저장명
    private String prOriginName;

    @Column(name = "Pr_jpg_path") //사진 저장경로
    private String prJpgPath;

    @Column(name = "Pr_jpg_name") //연습실 사진 고유이름
    private String prJpgName;

    public RoomPhoto(Integer jpgNum, Integer prNum, String prOriginName, String prJpgPath, String prJpgName) {
        this.jpgNum = jpgNum;
        this.prNum = prNum;
        this.prOriginName = prOriginName;
        this.prJpgPath = prJpgPath;
        this.prJpgName = prJpgName;
    }
}
