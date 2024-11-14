package com.spring.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "set_list")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class SetList {
    @Id
    @GeneratedValue
    @Column(name = "List_num") //리스트 번호
    private Integer listNum;

    @Column(name = "C_num") //커뮤니티 번호
    private Integer cNum;

    @Column(name = "List_title") //리스트 제목
    private String listTitle;

    @Column(name = "Team_interval)num") //팀 음정 점수
    private Integer teamInterval;

    @Column(name = "Team_beat_num") //팀 박자 점수
    private Integer teamBeatNum;

    @Column(name = "Team_permance_num") //팀 퍼포먼스 점수
    private Integer teamPermanceNum;

    public SetList(Integer listNum, Integer cNum, String listTitle, Integer teamInterval, Integer teamBeatNum, Integer teamPermanceNum) {
        this.listNum = listNum;
        this.cNum = cNum;
        this.listTitle = listTitle;
        this.teamInterval = teamInterval;
        this.teamBeatNum = teamBeatNum;
        this.teamPermanceNum = teamPermanceNum;
    }
}
