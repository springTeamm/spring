package com.spring.demo.admin.userManager.repository;

import com.spring.demo.admin.userManager.entity.Hosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HostsRepository extends JpaRepository<Hosts, Integer> {
    Hosts findByUserNum(Integer userNum);
}
