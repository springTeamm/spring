package com.spring.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "list_personal_score")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ListPersonalScore {
    @Id
    @GeneratedValue
    @Column(name = "Personal_num") //개인점수번호
    private Integer personalNum;

    @Column(name = "Play_list_num") //뮤직리스트 번호
    private Integer playListNum;

    @Column(name = "User_num")// 유저 번호
    private Integer userNum;

    @Column(name = "Personal_interval_num") //개인 음정 점수
    private Integer personalIntervalNum;

    @Column(name = "Personal_beat_num") //개인 박자 점수
    private Integer personalBeatNum;

    @Column(name = "Personal_performance_num")//개인 수행 점수
    private Integer personalPerformanceNum;

    public ListPersonalScore(Integer personalNum, Integer playListNum, Integer userNum, Integer personalIntervalNum, Integer personalBeatNum, Integer personalPerformanceNum) {
        this.personalNum = personalNum;
        this.playListNum = playListNum;
        this.userNum = userNum;
        this.personalIntervalNum = personalIntervalNum;
        this.personalBeatNum = personalBeatNum;
        this.personalPerformanceNum = personalPerformanceNum;
    }
}
