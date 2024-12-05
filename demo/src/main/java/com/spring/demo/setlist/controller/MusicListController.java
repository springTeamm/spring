package com.spring.demo.setlist.controller;

import com.spring.demo.setlist.entity.MusicList;
import com.spring.demo.setlist.service.MusicListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/musiclists")
@RequiredArgsConstructor
public class MusicListController {

    private final MusicListService musicListService;

    @GetMapping
    public List<MusicList> getAllMusicLists() {
        return musicListService.getAllMusicLists();
    }

}
