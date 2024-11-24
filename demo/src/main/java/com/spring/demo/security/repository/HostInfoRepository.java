package com.spring.demo.security.repository;

import com.spring.demo.security.model.HostEntity;
import com.spring.demo.security.model.HostInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostInfoRepository extends JpaRepository<HostInfoEntity, Long> {
    Optional<HostInfoEntity> findByHost(HostEntity host);
}
