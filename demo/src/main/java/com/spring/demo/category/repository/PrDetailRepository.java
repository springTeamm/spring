package com.spring.demo.category.repository;

import com.spring.demo.entity.PrDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface PrDetailRepository extends JpaRepository<PrDetail, Integer> {

    @Query("SELECT p FROM PrDetail p WHERE p.hostNum = :hostNum AND p.locationName = :locationName")
    List<PrDetail> findByHostNumAndLocationName(@Param("hostNum") Integer hostNum, @Param("locationName") String locationName);

    @Query("SELECT p FROM PrDetail p WHERE p.hostNum = :hostNum")
    List<PrDetail> findByHostNum(@Param("hostNum") Integer hostNum);
}
