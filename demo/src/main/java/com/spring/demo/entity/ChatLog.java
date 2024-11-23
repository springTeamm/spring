package com.spring.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "chat_log")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ChatLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Log_num") //채팅로그번호
    private Integer logNum;

    @Column(name = "User_num") //채팅친 유저 번호
    private Integer userNum;

    @Column(name = "Room_num") //채팅방 번호
    private Integer roomNum;

    @Column(name = "Log_text") //문의내용
    private String logText;

    @Column(name = "Log_chatting_time") //채팅내역 생성 날짜
    private LocalDateTime logChattingTime;

    public ChatLog(Integer logNum, Integer userNum, Integer roomNum, String logText, LocalDateTime logChattingTime) {
        this.logNum = logNum;
        this.userNum = userNum;
        this.roomNum = roomNum;
        this.logText = logText;
        this.logChattingTime = logChattingTime;
    }
}
