package com.spring.demo.setlist.repository;

import com.spring.demo.setlist.entity.ListPersonalScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListPersonalScoreRepository extends JpaRepository<ListPersonalScore, Integer> {
}
