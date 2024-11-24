package com.spring.demo.HostOCR;

import com.spring.demo.entity.HostInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostRepository extends JpaRepository<HostInfo, Integer> {

}
