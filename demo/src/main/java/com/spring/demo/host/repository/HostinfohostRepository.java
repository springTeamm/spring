package com.spring.demo.host.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostinfohostRepository extends JpaRepository<HostInfo,Integer> {
    Optional<HostInfo> findByHostInfoNum(Integer hostInfoNum);

    Optional<HostInfo> findByHostNum(Integer hostNum);
}
