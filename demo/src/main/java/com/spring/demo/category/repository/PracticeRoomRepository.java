package com.spring.demo.category.repository;

import com.spring.demo.entity.PracticeRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PracticeRoomRepository extends JpaRepository<PracticeRoom, Integer> {

    // 제목 키워드 검색
    List<PracticeRoom> findByPrNameContaining(String keyword);

    // 카테고리별 검색
    List<PracticeRoom> findByCategoryNum(Integer categoryNum);

    // 키워드와 카테고리 조합 검색
    List<PracticeRoom> findByPrNameContainingAndCategoryNum(String keyword, Integer categoryNum);
}