package com.spring.demo.admin.userManager.repository;

import com.spring.demo.admin.userManager.entity.PracticeRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PracticeRoomRepository extends JpaRepository<PracticeRoom, Integer> {
    List<PracticeRoom> findByHostInfoNum(Integer hostNum);
}
