package com.spring.demo.community.service;

import com.spring.demo.community.repository.CommunityPhotoRepository;
import com.spring.demo.entity.Community;
import com.spring.demo.entity.CommunityPhoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CommunityPhotoService {

    private final CommunityPhotoRepository communityPhotoRepository;
    private final String uploadDir;

    @Autowired
    public CommunityPhotoService(CommunityPhotoRepository communityPhotoRepository) {
        this.communityPhotoRepository = communityPhotoRepository;
        this.uploadDir = System.getProperty("user.dir") + "/uploads/"; // 이미지 저장 디렉토리 경로
        createUploadDirectory(); // 업로드 디렉토리 생성
    }

    // 업로드 디렉토리가 없으면 생성
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

    // 커뮤니티에 이미지를 저장하는 메서드
    public List<CommunityPhoto> saveCommunityPhotos(Community community, List<MultipartFile> images) {
        List<CommunityPhoto> photoList = new ArrayList<>();

        for (MultipartFile file : images) {
            String originalFilename = file.getOriginalFilename();

            // 파일 이름이 비어있으면 예외 처리
            if (originalFilename == null || originalFilename.trim().isEmpty()) {
                throw new IllegalArgumentException("파일 이름이 비어 있습니다.");
            }

            // 고유한 파일 이름 생성 (UUID 사용)
            String uniqueFilename = UUID.randomUUID() + "_" + originalFilename;
            String filePath = uploadDir + uniqueFilename;

            try {
                // 파일을 지정된 경로에 저장
                File uploadFile = new File(filePath);
                file.transferTo(uploadFile);

                // CommunityPhoto 엔티티 생성
                CommunityPhoto photo = new CommunityPhoto();
                photo.setCommunity(community);  // 해당 커뮤니티와 연결
                photo.setCJpgOriginName(originalFilename);  // 원본 파일명
                photo.setCJpgPath("/uploads/" + uniqueFilename);  // 클라이언트에서 접근할 수 있는 경로
                photo.setCJpgName(uniqueFilename);  // 저장된 고유 파일명
                photoList.add(photo);

            } catch (IOException e) {
                throw new RuntimeException("이미지 저장 실패: " + originalFilename, e);
            }
        }

        return photoList;
    }

    // 특정 커뮤니티의 이미지 조회
    public List<CommunityPhoto> getPhotosByCommunityId(Integer communityId) {
        List<CommunityPhoto> photos = communityPhotoRepository.findByCommunity_cNum(communityId);
        if (photos == null || photos.isEmpty()) {
            throw new RuntimeException("해당 커뮤니티에 대한 이미지를 찾을 수 없습니다.");
        }
        return photos;
    }

    // 이미지를 삭제하는 메서드 (선택 사항)
    public void deletePhoto(Integer photoId) {
        CommunityPhoto photo = communityPhotoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("해당 이미지가 존재하지 않습니다."));

        // 파일 삭제
        File file = new File(uploadDir + photo.getCJpgName());
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("파일 삭제 성공: " + file.getAbsolutePath());
            } else {
                System.out.println("파일 삭제 실패: " + file.getAbsolutePath());
            }
        }

        // 데이터베이스에서 이미지 삭제
        communityPhotoRepository.delete(photo);
    }
}
