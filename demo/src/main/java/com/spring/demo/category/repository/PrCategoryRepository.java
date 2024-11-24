package com.spring.demo.category.repository;


import com.spring.demo.entity.PrCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrCategoryRepository extends JpaRepository<PrCategory, Integer> {
}
