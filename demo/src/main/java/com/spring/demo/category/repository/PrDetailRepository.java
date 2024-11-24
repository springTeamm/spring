package com.spring.demo.category.repository;


import com.spring.demo.entity.PrDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrDetailRepository extends JpaRepository<PrDetail, Integer> {
}