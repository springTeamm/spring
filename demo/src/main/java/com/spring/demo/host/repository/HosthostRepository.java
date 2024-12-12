package com.spring.demo.host.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HosthostRepository extends JpaRepository<Hosts,Integer> {

    Optional<Hosts> findByUserNum(Integer userNum);
}
