package com.spring.demo.setlist.repository;

import com.spring.demo.setlist.entity.MusicList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicListRepository extends JpaRepository<MusicList, Long> {
}
