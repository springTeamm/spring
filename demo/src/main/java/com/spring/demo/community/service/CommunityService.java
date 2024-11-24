package com.spring.demo.community.service;

import com.spring.demo.community.repository.CommunityRepository;
import com.spring.demo.entity.Community;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityService {

    private final CommunityRepository communityRepository;

    @Autowired
    public CommunityService(CommunityRepository communityRepository) {
        this.communityRepository = communityRepository;
    }

    // 게시글 저장
    public Community saveCommunity(Community community) {
        return communityRepository.save(community);
    }

    // 모든 게시글 조회
    public List<Community> getAllCommunities() {
        return communityRepository.findAll();
    }

    // 카테고리별 게시글 조회
    public List<Community> getCommunitiesByCategory(Integer categoryNum) {
        return communityRepository.findByCategoryNum(categoryNum);
    }

    // 특정 글 조회
    public Community getCommunityById(Integer id) {
        return communityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID " + id + "에 해당하는 게시글이 없습니다."));
    }

    // 게시글 수정
    public Community updateCommunity(Integer id, Community updatedCommunity) {
        Community existingCommunity = communityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID " + id + "에 해당하는 게시글이 없습니다."));

        // 기존 데이터를 수정
        existingCommunity.setTitle(updatedCommunity.getTitle());
        existingCommunity.setText(updatedCommunity.getText());
        existingCommunity.setCategoryNum(updatedCommunity.getCategoryNum());
        existingCommunity.setDate(updatedCommunity.getDate());
        existingCommunity.setSchedule(updatedCommunity.getSchedule());
        existingCommunity.setLocation(updatedCommunity.getLocation());
        existingCommunity.setMembers(updatedCommunity.getMembers());
        existingCommunity.setLinks(updatedCommunity.getLinks());

        return communityRepository.save(existingCommunity);
    }
}
