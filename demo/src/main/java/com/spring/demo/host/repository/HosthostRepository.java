package com.spring.demo.host.repository;

import com.spring.demo.entity.Hosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HosthostRepository extends JpaRepository<Hosts,Integer> {
    Optional<Hosts> findByHostInfoNum(Integer hostInfoNum);

    Optional<Hosts> findByUser_UserNum(Integer userNum);
}
