package com.spring.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "chat_room")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ChatRoom {
    @Id
    @GeneratedValue
    @Column(name = "Room_num") //채팅방 번호
    private Integer roomNum;

    @Column(name = "Host_num") //호스트번호
    private Integer hostNum;

    @Column(name = "User_num") //유저번호
    private Integer userNum;

    @Column(name = "Room_making_date") //방 생성 날짜
    private Date roomMakingDate;

    @Column(name = "Chat_category_num") //방 카테고리 번호
    private Integer chatCategoryNum;

    public ChatRoom(Integer roomNum, Integer hostNum, Integer userNum, Date roomMakingDate, Integer chatCategoryNum) {
        this.roomNum = roomNum;
        this.hostNum = hostNum;
        this.userNum = userNum;
        this.roomMakingDate = roomMakingDate;
        this.chatCategoryNum = chatCategoryNum;
    }
}
