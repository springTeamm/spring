package com.spring.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "music_list")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class MusicList {
    @Id
    @GeneratedValue
    @Column(name = "Music_list_num") //뮤직리스트번호
    private Integer musicListNum;

    @Column(name = "List_num") //셋리스트 번호
    private Integer listNum;

    @Column(name = "Music_origin_name") //음악 본명
    private String musicOriginName;

    @Column(name = "Music_path")  //음악 저장 경로
    private String musicPath;

    @Column(name = "Music_name") //음악 고유명
    private String musicName;

    public MusicList(Integer musicListNum, Integer listNum, String musicOriginName, String musicPath, String musicName) {
        this.musicListNum = musicListNum;
        this.listNum = listNum;
        this.musicOriginName = musicOriginName;
        this.musicPath = musicPath;
        this.musicName = musicName;
    }
}
