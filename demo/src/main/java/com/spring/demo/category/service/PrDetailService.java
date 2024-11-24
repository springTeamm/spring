package com.spring.demo.category.service;

import com.spring.demo.entity.PrDetail;
import com.spring.demo.category.repository.PrDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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

    // location_name이 존재하는 데이터만 반환
    public List<PrDetail> getPrDetailsWithLocation() {
        return prDetailRepository.findAll().stream()
                .filter(detail -> detail.getLocationName() != null && !detail.getLocationName().isEmpty())
                .collect(Collectors.toList());
    }

    // 특정 연습실 조회
    public PrDetail getPrDetailById(Integer prNum) {
        return prDetailRepository.findById(prNum)
                .orElseThrow(() -> new RuntimeException("연습실을 찾을 수 없습니다. ID: " + prNum));
    }

    // 특정 HostNum에 해당하는 연습실 고유번호 목록 반환
    public List<Integer> getPrNumsByHostNum(Integer hostNum) {
        return prDetailRepository.findByHostNum(hostNum)
                .stream()
                .map(PrDetail::getPrNum)
                .collect(Collectors.toList());
    }

    public List<PrDetail> getUniqueLocationPrDetailsByHost(Integer hostNum, String locationName) {
        return prDetailRepository.findByHostNumAndLocationName(hostNum, locationName);
    }

    // 연습실 정보 필터링 조회
    public List<PrDetail> getFilteredPrDetails(Integer minPrice, Integer maxPrice, String category, String region, Integer spaceType) {
        return prDetailRepository.findAll().stream()
                .filter(detail -> minPrice == null || detail.getPrPrice() >= minPrice)
                .filter(detail -> maxPrice == null || detail.getPrPrice() <= maxPrice)
                .filter(detail -> category == null || category.trim().isEmpty() || detail.getPrWarnings().contains(category))
                .filter(detail -> region == null || region.trim().isEmpty() || detail.getPrAddress().contains(region))
                .filter(detail -> spaceType == null || detail.getPrSpaceType().equals(spaceType))
                .collect(Collectors.toList());
    }
    // 연습실 수정
    public PrDetail updatePrDetail(Integer id, PrDetail updatedPrDetail) {
        PrDetail existingPrDetail = prDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("연습실을 찾을 수 없습니다. ID: " + id));

        // 업데이트할 필드를 설정
        if (updatedPrDetail.getPrAddress() != null) existingPrDetail.setPrAddress(updatedPrDetail.getPrAddress());
        if (updatedPrDetail.getPrWarnings() != null) existingPrDetail.setPrWarnings(updatedPrDetail.getPrWarnings());
        if (updatedPrDetail.getPrParking() != null) existingPrDetail.setPrParking(updatedPrDetail.getPrParking());
        if (updatedPrDetail.getPrMaxPerson() != null) existingPrDetail.setPrMaxPerson(updatedPrDetail.getPrMaxPerson());
        if (updatedPrDetail.getPrPrice() != null) existingPrDetail.setPrPrice(updatedPrDetail.getPrPrice());
        if (updatedPrDetail.getPrSpaceType() != null) existingPrDetail.setPrSpaceType(updatedPrDetail.getPrSpaceType());
        if (updatedPrDetail.getPrName() != null) existingPrDetail.setPrName(updatedPrDetail.getPrName());
        if (updatedPrDetail.getPrOpenTime() != null) existingPrDetail.setPrOpenTime(updatedPrDetail.getPrOpenTime());
        if (updatedPrDetail.getPrCloseTime() != null) existingPrDetail.setPrCloseTime(updatedPrDetail.getPrCloseTime());
        if (updatedPrDetail.getPrDescription() != null)
            existingPrDetail.setPrDescription(updatedPrDetail.getPrDescription());

        return prDetailRepository.save(existingPrDetail);
    }
}
