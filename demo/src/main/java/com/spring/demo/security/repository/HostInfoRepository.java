package com.spring.demo.security.repository;

import com.spring.demo.security.entity.HostInfo;
import com.spring.demo.security.entity.Hosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostInfoRepository extends JpaRepository<HostInfo, Long> {

    Optional<HostInfo> findByHost(Hosts host);
}