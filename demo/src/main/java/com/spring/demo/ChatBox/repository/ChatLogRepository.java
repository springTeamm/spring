package com.spring.demo.ChatBox.repository;

import com.spring.demo.ChatBox.entity.ChatLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatLogRepository extends JpaRepository<ChatLog, Integer> {

    List<ChatLog> findByRoomNum(Integer roomNum);

    List<ChatLog> findByRoomNumOrderByLogChattingTime(Integer roomNum);
}
