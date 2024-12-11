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

    private final String uploadDir = System.getProperty("user.dir") + "/uploads/";

    @Autowired
    public CommunityService(CommunityRepository communityRepository, CommunityPhotoRepository communityPhotoRepository) {
        this.communityRepository = communityRepository;
        this.communityPhotoRepository = communityPhotoRepository;
        createUploadDirectory();
    }

    private void createUploadDirectory() {
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists() && !uploadDirFile.mkdirs()) {
            throw new RuntimeException("Failed to create upload directory: " + uploadDir);
        }
    }

    public Community saveCommunityWithImages(Community community, List<MultipartFile> images) {
        if (community.getCategoryNum() == null || community.getCategoryNum() < 1 || community.getCategoryNum() > 3) {
            throw new IllegalArgumentException("Invalid category number: " + community.getCategoryNum());
        }

        community.setDate(LocalDateTime.now());
        Community savedCommunity = communityRepository.save(community);

        if (images != null && !images.isEmpty()) {
            List<CommunityPhoto> photoList = saveImages(savedCommunity, images);
            communityPhotoRepository.saveAll(photoList);
        }

        return savedCommunity;
    }

    private List<CommunityPhoto> saveImages(Community community, List<MultipartFile> images) {
        List<CommunityPhoto> photoList = new ArrayList<>();

        for (MultipartFile file : images) {
            String originalFilename = file.getOriginalFilename();

            if (originalFilename == null || originalFilename.trim().isEmpty()) {
                throw new IllegalArgumentException("File name is empty.");
            }

            String uniqueFilename = UUID.randomUUID() + "_" + originalFilename;
            String filePath = uploadDir + uniqueFilename;

            try {
                File uploadFile = new File(filePath);
                file.transferTo(uploadFile);

                CommunityPhoto photo = new CommunityPhoto();
                photo.setCommunity(community);
                photo.setCJpgOriginName(originalFilename);
                photo.setCJpgPath("/uploads/" + uniqueFilename);
                photo.setCJpgName(uniqueFilename);
                photoList.add(photo);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save image: " + originalFilename, e);
            }
        }

        return photoList;
    }

    public List<CommunityPhoto> findPhotosByCommunityId(Integer id) {
        return communityPhotoRepository.findByCommunity_cNum(id);
    }
    public Community getCommunityWithImages(Integer id) {
        Community community = communityRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID " + id + "에 해당하는 게시글이 없습니다."));

        List<CommunityPhoto> photos = communityPhotoRepository.findByCommunity_cNum(id);
        community.setPhotos(photos);

        return community;
    }

    public List<Community> getAllCommunities() {
        return communityRepository.findAll();
    }

    public List<Community> getCommunitiesByCategory(Integer categoryNum) {
        if (categoryNum < 1 || categoryNum > 3) {
            throw new IllegalArgumentException("유효하지 않은 카테고리 번호입니다: " + categoryNum);
        }
        return communityRepository.findByCategoryNum(categoryNum);
    }
}