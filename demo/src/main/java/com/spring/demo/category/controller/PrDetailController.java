package com.spring.demo.category.controller;

import com.spring.demo.category.service.PrDetailService;
import com.spring.demo.entity.PrDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prdetails")
public class PrDetailController {

    private final PrDetailService prDetailService;

    @Autowired
    public PrDetailController(PrDetailService prDetailService) {
        this.prDetailService = prDetailService;
    }

    // 연습실 등록
    @PostMapping
    public PrDetail createPrDetail(@RequestBody PrDetail prDetail) {
        return prDetailService.savePrDetail(prDetail);
    }

    // location_name이 존재하는 데이터만 필터링 조회
    @GetMapping("/with-location")
    public List<PrDetail> getPrDetailsWithLocation() {
        return prDetailService.getPrDetailsWithLocation();
    }

    // 특정 연습실 조회
    @GetMapping("/{id}")
    public PrDetail getPrDetailById(@PathVariable Integer id) {
        return prDetailService.getPrDetailById(id);
    }

    @GetMapping("/host-rooms/{hostNum}")
    public List<Integer> getPrNumsByHost(@PathVariable Integer hostNum) {
        return prDetailService.getPrNumsByHostNum(hostNum);
    }

    @GetMapping("/host-rooms-unique/{hostNum}")
    public List<PrDetail> getUniqueLocationPrDetailsByHost(
            @PathVariable Integer hostNum,
            @RequestParam String locationName) {
        return prDetailService.getUniqueLocationPrDetailsByHost(hostNum, locationName);
    }

    // 연습실 정보 필터링 조회
    @GetMapping
    public List<PrDetail> getFilteredPrDetails(
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) Integer spaceType) {
        return prDetailService.getFilteredPrDetails(minPrice, maxPrice, category, region, spaceType);
    }

    // 연습실 수정
    @PutMapping("/{id}")
    public PrDetail updatePrDetail(@PathVariable Integer id, @RequestBody PrDetail updatedPrDetail) {
        return prDetailService.updatePrDetail(id, updatedPrDetail);
    }
}
