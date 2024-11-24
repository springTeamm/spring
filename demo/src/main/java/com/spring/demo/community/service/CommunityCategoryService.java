package com.spring.demo.community.service;

import com.spring.demo.community.repository.CommunityCategoryRepository;
import com.spring.demo.entity.CommunityCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityCategoryService {

    @Autowired
    private CommunityCategoryRepository communityCategoryRepository;

    public List<CommunityCategory> getAllCategories() {
        return communityCategoryRepository.findAll();
    }
}
