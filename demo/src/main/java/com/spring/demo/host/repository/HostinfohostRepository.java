package com.spring.demo.host.repository;

import com.spring.demo.entity.HostInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostinfohostRepository extends JpaRepository<HostInfo,Integer> {
    HostInfo findByHostInfoNum(Integer hostInfoNum);
}
