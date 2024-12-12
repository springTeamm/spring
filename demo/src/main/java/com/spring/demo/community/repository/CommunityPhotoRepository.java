package com.spring.demo.community.repository;

import com.spring.demo.entity.CommunityPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityPhotoRepository extends JpaRepository<CommunityPhoto, Integer> {

    // 특정 커뮤니티에 속한 사진 리스트 조회
    @Query("SELECT cp FROM CommunityPhoto cp WHERE cp.community.cNum = :cNum")
    List<CommunityPhoto> findByCommunity_cNum(@Param("cNum") Integer cNum);
}
