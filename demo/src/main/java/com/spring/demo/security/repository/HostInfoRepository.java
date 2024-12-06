package com.spring.demo.security.repository;

import com.spring.demo.security.entity.HostInfo;
import com.spring.demo.security.entity.Hosts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostInfoRepository extends JpaRepository<HostInfo, Integer> {

    // 호스트 등록 번호로 호스트 정보 찾기
    Optional<HostInfo> findByHostRegistNum(String hostRegistNum);

    HostInfo findByHost_HostNum(Integer hostName);




}