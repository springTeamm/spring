package com.spring.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "chat_category")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ChatCategory {
    @Id
    @GeneratedValue
    @Column(name = "Chat_category_num") //카테고리번호
    private Integer chatCategoryNum;

    @Column(name = "Chat_category_name") //방카테고리명
    private String chatCategoryName;

    public ChatCategory(Integer chatCategoryNum, String chatCategoryName) {
        this.chatCategoryNum = chatCategoryNum;
        this.chatCategoryName = chatCategoryName;
    }
}
