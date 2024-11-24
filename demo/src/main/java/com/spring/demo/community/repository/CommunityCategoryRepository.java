package com.spring.demo.community.repository;

import com.spring.demo.entity.CommunityCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityCategoryRepository extends JpaRepository<CommunityCategory, Integer> {
}
