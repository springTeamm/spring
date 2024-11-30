package com.spring.demo.community.service;

import com.spring.demo.community.repository.CommunityPhotoRepository;
import com.spring.demo.community.repository.CommunityRepository;
import com.spring.demo.entity.Community;
import com.spring.demo.entity.CommunityPhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityPhotoRepository communityPhotoRepository;

    // 업로드 디렉토리 경로 설정 (프로젝트 루트 기준)
    private final String uploadDir = System.getProperty("user.dir") + "/uploads/";

    @Autowired
    public CommunityService(CommunityRepository communityRepository, CommunityPhotoRepository communityPhotoRepository) {
        this.communityRepository = communityRepository;
        this.communityPhotoRepository = communityPhotoRepository;
        createUploadDirectory(); // 애플리케이션 시작 시 디렉토리 생성
    }

    // uploads 디렉토리 생성
    private void createUploadDirectory() {
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            if (uploadDirFile.mkdirs()) {
                System.out.println("Upload directory created at: " + uploadDir);
            } else {
                throw new RuntimeException("Failed to create upload directory: " + uploadDir);
            }
        }
    }

    // 게시글 저장 (이미지 포함)
    public Community saveCommunityWithImages(Community community, List<MultipartFile> images) {
        if (community.getCategoryNum() == null || community.getCategoryNum() < 1 || community.getCategoryNum() > 3) {
            throw new IllegalArgumentException("유효하지 않은 카테고리 번호입니다: " + community.getCategoryNum());
        }

        // 작성 날짜 자동 설정
        community.setDate(LocalDateTime.now());

        Community savedCommunity = communityRepository.save(community);

        if (images != null && !images.isEmpty()) {
            List<CommunityPhoto> photoList = saveImages(savedCommunity, images);
            communityPhotoRepository.saveAll(photoList); // 이미지 정보 저장
        }

        return savedCommunity;
    }

    // 이미지 저장
    private List<CommunityPhoto> saveImages(Community community, List<MultipartFile> images) {
        List<CommunityPhoto> photoList = new ArrayList<>();

        for (MultipartFile file : images) {
            String originalFilename = file.getOriginalFilename();

            // 파일 이름 확인 및 기본값 처리
            if (originalFilename == null || originalFilename.trim().isEmpty()) {
                throw new IllegalArgumentException("파일 이름이 비어 있습니다.");
            }

            // 고유한 파일 이름 생성
            String uniqueFilename = UUID.randomUUID() + "_" + originalFilename;
            String filePath = uploadDir + uniqueFilename;

            try {
                // 파일 저장
                File uploadFile = new File(filePath);
                file.transferTo(uploadFile);

                // CommunityPhoto 엔티티 생성
                CommunityPhoto photo = new CommunityPhoto();
                photo.setCommunity(community);
                photo.setCJpgOriginName(originalFilename);
                photo.setCJpgPath("/uploads/" + uniqueFilename); // 상대 URL 설정
                photo.setCJpgName(uniqueFilename);
                photoList.add(photo);
            } catch (IOException e) {
                throw new RuntimeException("이미지 저장 실패: " + originalFilename, e);
            }
        }

        return photoList;
    }

    // 특정 게시글 조회 (이미지 포함)
    public Community getCommunityWithImages(Integer id) {
        Community community = communityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID " + id + "에 해당하는 게시글이 없습니다."));

        // 해당 커뮤니티에 속한 이미지 리스트를 추가
        List<CommunityPhoto> photos = communityPhotoRepository.findByCommunity_cNum(id);
        community.setPhotos(photos); // 이미지 설정

        return community;
    }

    // 특정 게시글에 속한 이미지 조회
    public List<CommunityPhoto> findPhotosByCommunityId(Integer id) {
        List<CommunityPhoto> photos = communityPhotoRepository.findByCommunity_cNum(id);
        if (photos == null || photos.isEmpty()) {
            throw new IllegalArgumentException("해당 게시글에 대한 이미지를 찾을 수 없습니다: " + id);
        }
        return photos;
    }

    // 모든 게시글 조회
    public List<Community> getAllCommunities() {
        return communityRepository.findAll();
    }

    // 카테고리별 게시글 조회
    public List<Community> getCommunitiesByCategory(Integer categoryNum) {
        return communityRepository.findByCategoryNum(categoryNum);
    }
}
