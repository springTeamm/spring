package com.spring.demo.host.repository;

import com.spring.demo.entity.Adjustment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public interface HostAdjustmentRepository extends JpaRepository<Adjustment, Integer> {
    List<Adjustment> findAll();

    @Modifying
    @Transactional
    @Query("DELETE FROM Adjustment a WHERE a.payNum = :payNum")
    void deleteByPayNum(@Param("payNum") Integer payNum);

}