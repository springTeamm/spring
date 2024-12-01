package com.spring.demo.host.repository;

import com.spring.demo.entity.PracticeRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostPracticeRoomRepository extends JpaRepository<PracticeRoom, Integer> {

    Optional<PracticeRoom> findByPrNum(Integer prNum);
}

