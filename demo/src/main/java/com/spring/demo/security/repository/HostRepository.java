package com.spring.demo.security.repository;

import com.spring.demo.security.model.HostEntity;
import com.spring.demo.security.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostRepository extends JpaRepository<HostEntity, Long> {
    Optional<HostEntity> findByUser(UserEntity user);
}
