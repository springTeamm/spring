package com.spring.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_room")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Room_num") //채팅방 번호
    private Integer roomNum;

    @Column(name = "Host_num") //호스트번호
    private Integer hostNum;

    @Column(name = "User_num") //유저번호
    private Integer userNum;

    @Column(name = "Room_making_time") //방 생성 날짜
    private LocalDateTime roomMakingtime;

    @Column(name = "Chat_category_num") //방 카테고리 번호
    private Integer chatCategoryNum;

    public ChatRoom(Integer roomNum, Integer hostNum, Integer userNum, LocalDateTime roomMakingtime, Integer chatCategoryNum) {
        this.roomNum = roomNum;
        this.hostNum = hostNum;
        this.userNum = userNum;
        this.roomMakingtime = roomMakingtime;
        this.chatCategoryNum = chatCategoryNum;
    }
}
