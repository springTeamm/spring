package com.spring.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "community_jpg")
@Getter
@Setter
@NoArgsConstructor
public class CommunityPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C_jpg_num")
    private Integer cJpgNum;

    @Column(name = "C_jpg_origin_name", nullable = false)
    private String cJpgOriginName;

    @Column(name = "C_jpg_path", nullable = false)
    private String cJpgPath;

    @Column(name = "C_jpg_name", nullable = false)
    private String cJpgName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "C_num", nullable = false)
    @JsonIgnore
    private Community community;

    // 생성자 정의
    public CommunityPhoto(Integer cJpgNum, String cJpgOriginName, String cJpgPath, String cJpgName, Community community) {
        this.cJpgNum = cJpgNum;
        this.cJpgOriginName = cJpgOriginName;
        this.cJpgPath = cJpgPath;
        this.cJpgName = cJpgName;
        this.community = community;
    }
}
