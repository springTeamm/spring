package com.spring.demo.community.controller;

import com.spring.demo.community.service.CommunityCategoryService;
import com.spring.demo.community.service.CommunityService;
import com.spring.demo.entity.Community;
import com.spring.demo.entity.CommunityCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/community")
public class CommunityController {

    private final CommunityService communityService;
    private final CommunityCategoryService communityCategoryService;

    @Autowired
    public CommunityController(CommunityService communityService, CommunityCategoryService communityCategoryService) {
        this.communityService = communityService;
        this.communityCategoryService = communityCategoryService;
    }

    // 게시글 작성
    @PostMapping
    public Community createCommunity(@RequestBody Community community) {
        return communityService.saveCommunity(community);
    }

    // 전체 글 보기
    @GetMapping
    public List<Community> getAllCommunities() {
        return communityService.getAllCommunities();
    }

    // 특정 카테고리 글 보기
    @GetMapping("/category/{categoryNum}")
    public List<Community> getCommunitiesByCategory(@PathVariable Integer categoryNum) {
        return communityService.getCommunitiesByCategory(categoryNum);
    }

    // 특정 글 조회
    @GetMapping("/{id}")
    public Community getCommunityById(@PathVariable Integer id) {
        return communityService.getCommunityById(id);
    }

    // 특정 글 수정
    @PutMapping("/{id}")
    public Community updateCommunity(@PathVariable Integer id, @RequestBody Community updatedCommunity) {
        return communityService.updateCommunity(id, updatedCommunity);
    }

    // 모든 커뮤니티 카테고리 조회
    @GetMapping("/categories")
    public List<CommunityCategory> getAllCategories() {
        return communityCategoryService.getAllCategories();
    }
}
