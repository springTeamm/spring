package com.spring.demo.repository;

import com.spring.demo.entity.ChatLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatLogRepository extends JpaRepository<ChatLog, Integer> {

    List<ChatLog> findByRoomNum(Integer roomNum);
}
