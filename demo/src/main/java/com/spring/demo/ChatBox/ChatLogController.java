package com.spring.demo.ChatBox;

import com.spring.demo.entity.ChatLog;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatLogController {

    @Autowired
    private ChatLogService chatLogService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @GetMapping("api/chatrooms/{roomNum}")
    public ResponseEntity<List<ChatLog>> getChatLogs(@PathVariable("roomNum") Integer roomNum) {
        List<ChatLog> chatLogs = chatLogService.getChatLogsByRoom(roomNum);
        return ResponseEntity.ok(chatLogs);
    }

/*    //메세지 저장
    @PostMapping("api/chatrooms/{roomNum}/messages/users/{userNum}")
    public ResponseEntity<ChatLog> addMessage(
            @PathVariable Integer roomNum,
            @PathVariable Integer userNum,
            @RequestBody String chatMessage
    ){
        ChatLog chatLogMessage = chatLogService.addChatMessage(roomNum, userNum, chatMessage);
        return ResponseEntity.ok(chatLogMessage);
    }*/

    @MessageMapping("chatrooms/{roomNum}/message")
    @SendTo("/topic/chatrooms/{roomNum}") // 메시지를 클라이언트로 전송
    public ChatMessage sendMessage(
            @DestinationVariable("roomNum") Integer roomNum,
            @Payload ChatMessage chatMessage
    ) {

        ChatLog chatLogMessage = chatLogService.addChatMessage(chatMessage.getRoomNum(), chatMessage.getUserNum(), chatMessage.getMessage());

        System.out.println("방번호 : " + roomNum);
        System.out.println("유저번호 : " + chatLogMessage.getUserNum());
        System.out.println("채팅내역 : " + chatMessage.getMessage());
        return chatMessage;
    }


}
