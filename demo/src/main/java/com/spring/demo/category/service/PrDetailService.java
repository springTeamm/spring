package com.spring.demo.category.service;

import com.spring.demo.entity.PrDetail;
import com.spring.demo.category.repository.PrDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class PrDetailService {

    private final PrDetailRepository prDetailRepository;

    @Autowired
    public PrDetailService(PrDetailRepository prDetailRepository) {
        this.prDetailRepository = prDetailRepository;
    }

    // 연습실 저장
    public PrDetail savePrDetail(PrDetail prDetail) {
        return prDetailRepository.save(prDetail);
    }

    // 특정 연습실 조회
    public PrDetail getPrDetailById(Integer id) {
        return prDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("연습실을 찾을 수 없습니다. ID: " + id));
    }

    public List<PrDetail> getFilteredPrDetails(Integer minPrice, Integer maxPrice, String category, String region, Integer spaceType) {
        return prDetailRepository.findAll().stream()
                .filter(detail -> minPrice == null || detail.getPrPrice() >= minPrice)
                .filter(detail -> maxPrice == null || detail.getPrPrice() <= maxPrice)
                .filter(detail -> category == null || category.trim().isEmpty() || detail.getPrWarnings().contains(category))
                .filter(detail -> region == null || region.trim().isEmpty() || detail.getPrAddress().contains(region))
                .filter(detail -> spaceType == null || detail.getPrSpaceType().equals(spaceType))
                .collect(Collectors.toList());
    }
}

