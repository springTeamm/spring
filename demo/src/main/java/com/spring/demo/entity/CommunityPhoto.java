package com.spring.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "community_jpg")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CommunityPhoto {
    @Id
    @GeneratedValue
    @Column(name = "C_jpg_num") //커뮤니티 사진 번호
    private Integer cJpgNum;

    @Column(name = "C_num") //커뮤니티 번호
    private Integer cNum;

    @Column(name = "C_jpg_origin_name") //커뮤니티 사진 본명
    private String cJpgOriginName;

    @Column(name = "C_jpg_path") //사진 경로명
    private String cJpgPath;

    @Column(name = "C_jpg_name") //사진 고유 이름
    private String cJpgName;

    public CommunityPhoto(Integer cJpgNum, Integer cNum, String cJpgOriginName, String cJpgPath, String cJpgName) {
        this.cJpgNum = cJpgNum;
        this.cNum = cNum;
        this.cJpgOriginName = cJpgOriginName;
        this.cJpgPath = cJpgPath;
        this.cJpgName = cJpgName;
    }


}
