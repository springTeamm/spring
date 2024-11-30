package com.spring.demo.security.repository;

import com.spring.demo.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserEmail(String userEmail);
    Optional<User> findByUserId(String userId);
    boolean existsByUserEmail(String email);
    boolean existsByUserId(String userId);
}