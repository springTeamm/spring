package com.spring.demo.security.repository;

import com.spring.demo.security.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUserEmail(String userEmail);
    Optional<Users> findByUserId(String userId);
    boolean existsByEmail(String email);
    boolean existsByUserId(String userId);
}