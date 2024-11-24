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

    // 연습실 정보 조회
    @GetMapping
    public List<PrDetail> getFilteredPrDetails(
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) Integer spaceType) {
        return prDetailService.getFilteredPrDetails(minPrice, maxPrice, category, region, spaceType);
    }

    // 특정 연습실 조회
    @GetMapping("/{id}")
    public PrDetail getPrDetailById(@PathVariable Integer id) {
        return prDetailService.getPrDetailById(id);
    }

}
