package com.spring.demo.setlist.service;

import com.spring.demo.setlist.entity.MusicList;
import com.spring.demo.setlist.repository.MusicListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicListService {

    private final MusicListRepository musicListRepository;

    // 생성자 주입
    public MusicListService(MusicListRepository musicListRepository) {
        this.musicListRepository = musicListRepository;
    }

    public List<MusicList> getAllMusicLists() {
        return musicListRepository.findAll();
    }

}
