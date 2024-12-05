package com.spring.demo.setlist.service;

import com.spring.demo.setlist.entity.SetList;
import com.spring.demo.setlist.repository.SetListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SetListService {

    private final SetListRepository setListRepository;

    public List<SetList> getAllSetLists() {
        return setListRepository.findAll();
    }

    public SetList getSetList(Long id) {
        return setListRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("SetList not found"));
    }
}
