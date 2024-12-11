package com.spring.demo.category.controller;

import com.spring.demo.category.service.PracticeRoomService;
import com.spring.demo.entity.PracticeRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost:4000")
public class PostController {

    private final PracticeRoomService practiceRoomService;

    @Autowired
    public PostController(PracticeRoomService practiceRoomService) {
        this.practiceRoomService = practiceRoomService;
    }

    // 게시글 목록 조회
    @GetMapping
    public List<PracticeRoom> getAllPosts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer categoryId
    ) {
        return practiceRoomService.searchPosts(keyword, categoryId);
    }

    // 특정 게시글 상세 조회
    @GetMapping("/{id}")
    public PracticeRoom getPostById(@PathVariable Integer id) {
        PracticeRoom post = practiceRoomService.getPostById(id);
        if (post == null) {
            throw new RuntimeException("게시글을 찾을 수 없습니다. ID: " + id);
        }
        return post;
    }
}