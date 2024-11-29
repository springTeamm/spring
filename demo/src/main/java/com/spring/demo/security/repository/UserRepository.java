package com.spring.demo.security.repository;

import com.spring.demo.security.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUserEmail(String userEmail);
    Optional<Users> findByUserId(String userId);
    boolean existsByUserEmail(String email);
    boolean existsByUserId(String userId);
}