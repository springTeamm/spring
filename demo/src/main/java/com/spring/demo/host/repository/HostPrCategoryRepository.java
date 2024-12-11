package com.spring.demo.host.repository;

import com.spring.demo.entity.PrCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface HostPrCategoryRepository extends JpaRepository<PrCategory, Integer> {
   }