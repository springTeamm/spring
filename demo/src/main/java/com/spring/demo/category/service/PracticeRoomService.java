package com.spring.demo.category.service;

import com.spring.demo.category.repository.PracticeRoomRepository;
import com.spring.demo.entity.PracticeRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PracticeRoomService {

    private final PracticeRoomRepository practiceRoomRepository;

    @Autowired
    public PracticeRoomService(PracticeRoomRepository practiceRoomRepository) {
        this.practiceRoomRepository = practiceRoomRepository;
    }

    // 키워드와 카테고리로 게시글 검색
    public List<PracticeRoom> searchPosts(String keyword, Integer categoryId) {
        if (keyword != null && categoryId != null) {
            return practiceRoomRepository.findByPrNameContainingAndCategoryNum(keyword, categoryId);
        } else if (keyword != null) {
            return practiceRoomRepository.findByPrNameContaining(keyword);
        } else if (categoryId != null) {
            return practiceRoomRepository.findByCategoryNum(categoryId);
        } else {
            return practiceRoomRepository.findAll();
        }
    }

    // 특정 게시글 상세 조회
    public PracticeRoom getPostById(Integer id) {
        return practiceRoomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다. ID: " + id));
    }
}
