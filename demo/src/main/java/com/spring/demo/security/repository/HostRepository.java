package com.spring.demo.security.repository;

import com.spring.demo.security.model.HostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostRepository extends JpaRepository<HostEntity, Long> {
}
