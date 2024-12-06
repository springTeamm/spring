package com.spring.demo.security.repository;

import com.spring.demo.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // 사용자 ID로 사용자 찾기
    User findByUserId(String userId);

    User findByUserEmail(String email);

    // 사용자 ID 중복 확인
    boolean existsByUserId(String userId);

    // 이메일 중복 확인
    boolean existsByUserEmail(String userEmail);

}