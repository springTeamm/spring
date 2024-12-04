package com.spring.demo.admin.userManager.repository;

import com.spring.demo.admin.userManager.entity.HostInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostInfoRepository extends JpaRepository<HostInfo, Integer> {
    List<HostInfo> findByHostNum(Integer hostNum);
}
