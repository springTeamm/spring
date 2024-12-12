package com.spring.demo.community.repository;

import com.spring.demo.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Integer> {

    // 카테고리 번호로 게시글 조회
    List<Community> findByCategoryNum(Integer categoryNum);
}
