package com.spring.demo.HostOCR;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.spring.demo.security.entity.HostInfo;

@Repository
public interface OcrHostRepository extends JpaRepository<HostInfo, Integer> {

}
