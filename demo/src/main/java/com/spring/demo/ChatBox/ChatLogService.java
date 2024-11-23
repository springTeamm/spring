package com.spring.demo.ChatBox;

import com.spring.demo.entity.ChatLog;
import com.spring.demo.entity.ChatRoom;
import com.spring.demo.repository.ChatLogRepository;
import com.spring.demo.repository.ChatRoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatLogService {

    @Autowired
    private ChatLogRepository chatLogRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    public List<ChatLog> getChatLogsByRoom(Integer roomNum){
        return chatLogRepository.findByRoomNum(roomNum);
    }

    @Transactional
    public ChatLog addChatMessage(Integer roomNum, Integer userNum, String chatMessage) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomNum)
                .orElseThrow(() -> new IllegalArgumentException(roomNum + "해당 번호의 방 존재하지 않음"));

        ChatLog chatLog = new ChatLog();
        chatLog.setRoomNum(roomNum);
        chatLog.setUserNum(userNum);
        chatLog.setLogText(chatMessage);
        chatLog.setLogChattingTime(LocalDateTime.now());

        return chatLogRepository.save(chatLog);
    }
}
