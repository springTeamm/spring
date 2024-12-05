package com.spring.demo.setlist.controller;

import com.spring.demo.setlist.entity.SetList;
import com.spring.demo.setlist.service.SetListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/setlists")
@RequiredArgsConstructor
public class SetListController {
    private final SetListService setListService;

    @GetMapping
    public List<SetList> getAllSetLists() {
        return setListService.getAllSetLists();
    }

}
