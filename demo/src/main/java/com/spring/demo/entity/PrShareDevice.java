package com.spring.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pr_share_device")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PrShareDevice {
    @Id
    @GeneratedValue
    @Column(name = "Pr_share_num")  // 공유물품번호
    private Integer prShareNum;

    @Column(name = "Pr_num") // 연습실 번호
    private Integer prNum;

    @Column(name = "Pr_share_name") //공유 품목 이름
    private String prShareName;

    public PrShareDevice(Integer prShareNum, Integer prNum, String prShareName) {
        this.prShareNum = prShareNum;
        this.prNum = prNum;
        this.prShareName = prShareName;
    }
}
