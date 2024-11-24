package com.spring.demo.ChatBox.service;

import com.spring.demo.entity.ChatRoom;
import com.spring.demo.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;


    public List<ChatRoom> getAllChatRooms() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        for(ChatRoom chatRoom : chatRooms) {
            System.out.println(chatRoom);
        }
        return chatRooms;
    }
}

