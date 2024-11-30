package com.spring.demo.community.controller;

import com.spring.demo.community.service.CommunityService;
import com.spring.demo.entity.Community;
import com.spring.demo.entity.CommunityPhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/community")
@CrossOrigin(origins = "http://localhost:4000")
public class CommunityController {

    private final CommunityService communityService;

    @Autowired
    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    // 게시글 작성
    @PostMapping
    public ResponseEntity<Community> createCommunity(
            @RequestPart("community") Community community,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        Community savedCommunity = communityService.saveCommunityWithImages(community, images);
        return new ResponseEntity<>(savedCommunity, HttpStatus.CREATED);
    }

    // 특정 게시글의 이미지 가져오기
    @GetMapping("/{id}/images")
    public ResponseEntity<List<Map<String, String>>> getCommunityImages(@PathVariable Integer id) {
        List<CommunityPhoto> photos = communityService.findPhotosByCommunityId(id);

        if (photos == null || photos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Map<String, String>> photoData = photos.stream()
                .map(photo -> Map.of(
                        "cJpgPath", "/uploads/" + photo.getCJpgName(), // 상대 경로로 반환
                        "cJpgOriginName", photo.getCJpgOriginName()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(photoData);
    }

    // 특정 글 조회 (이미지 포함)
    @GetMapping("/{id}")
    public ResponseEntity<Community> getCommunityWithImages(@PathVariable Integer id) {
        Community community = communityService.getCommunityWithImages(id);
        return ResponseEntity.ok(community);
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
}
