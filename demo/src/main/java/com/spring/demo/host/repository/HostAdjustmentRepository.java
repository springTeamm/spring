package com.spring.demo.host.repository;

import com.spring.demo.entity.Adjustment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HostAdjustmentRepository extends JpaRepository<Adjustment, Integer> {
    List<Adjustment> findAll();
}