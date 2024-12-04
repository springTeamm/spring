package com.spring.demo.admin.userManager.repository;

import com.spring.demo.admin.userManager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserManagerRepository extends JpaRepository<User, Integer> {
}
