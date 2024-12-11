package com.spring.demo.host.repository;

import com.spring.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostUserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByUserNum(Integer userNum);


    Optional<User> findByUserId(String userId);
}
