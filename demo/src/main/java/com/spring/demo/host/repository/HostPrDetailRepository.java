package com.spring.demo.host.repository;

import com.spring.demo.entity.PrDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostPrDetailRepository extends JpaRepository<PrDetail,Integer> {
    Optional<PrDetail> deleteByPrNum(Integer roomId);
    Optional<PrDetail> findById(Integer bookingNum);
}
