package com.spring.demo.security.repository;

import com.spring.demo.security.entity.Hosts;
import com.spring.demo.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("securityHostRepository")
public interface HostRepository extends JpaRepository<Hosts, Long> {

    Optional<Hosts> findByUser(User user);
}
