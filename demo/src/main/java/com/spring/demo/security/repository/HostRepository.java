package com.spring.demo.security.repository;


import com.spring.demo.security.entity.Hosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("securityHostRepository")
public interface HostRepository extends JpaRepository<Hosts, Integer> {

    // 특정 사용자의 호스트 정보 찾기
    Hosts findByUser_UserNum(Integer userNum);

    Hosts findByUser_UserId(String userId);

}
