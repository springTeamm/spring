package com.spring.demo.host.repository;

import com.spring.demo.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface HostRefund extends JpaRepository<Refund, Integer> {

    Optional<Refund> deleteByPayNum(Integer roomId);

    Optional<Refund> findByPayNum(Integer payNum);
}
