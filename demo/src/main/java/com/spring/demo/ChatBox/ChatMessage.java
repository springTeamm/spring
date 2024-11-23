package com.spring.demo.ChatBox;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {
    private Integer roomNum;

    @JsonProperty("logText")
    private String message;

    private Integer userNum;

    public ChatMessage(Integer roomNum, String message, Integer userNum) {
        this.roomNum = roomNum;
        this.message = message;
        this.userNum = userNum;
    }
}
