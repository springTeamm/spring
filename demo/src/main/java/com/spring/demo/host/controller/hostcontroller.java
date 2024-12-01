package com.spring.demo.host.controller;


import com.spring.demo.category.service.PracticeRoomService;
import com.spring.demo.entity.PrReview;
import com.spring.demo.host.DTO.*;

import com.spring.demo.host.service.HostCancellService;

import com.spring.demo.host.service.ReservationService;
import com.spring.demo.host.service.ReviewService;
import com.spring.demo.host.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hostpage")
public class hostcontroller {

    private final ReviewService reviewService;
    private final ReservationService reservationService;
    private final HostCancellService hostcancellService;
    private final SalesService salesService;

    @Autowired
    private HostCancellService hostCancellService;
//    private final HostPageService hostPageService;

    @Autowired
    public hostcontroller(ReviewService reviewService, ReservationService reservationService, HostCancellService hostcancellService
    , SalesService salesService) {
        this.reviewService = reviewService;
//        this.hostPageService = hostPageService;
        this.reservationService = reservationService;
        this.hostcancellService = hostcancellService;
        this.salesService = salesService;
    }

    // 1. 리뷰 목록 가져오기
    @GetMapping("/review")
    public ResponseEntity<List<HostReviewDTO>> getAllReviews() {
        List<HostReviewDTO> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }
    // 개별 답글 등록
    @PostMapping("/review/{reviewId}/reply")
    public ResponseEntity<String> addIndividualReply(
            @PathVariable Integer reviewId,
            @RequestParam String text) {
        reviewService.addReply(reviewId, text);
        return ResponseEntity.ok("개별 답글이 등록되었습니다.");
    }

    // 공통 답글 등록
    @PostMapping("/reply")
    public ResponseEntity<String> addCommonReply(@RequestBody List<HostReplyDTO> replies) {
        reviewService.addCommonReplies(replies);
        return ResponseEntity.ok("공통 답글이 등록되었습니다.");
    }

//    @GetMapping("hostinfo/{userNum}")
//    public ResponseEntity<HostPageDTO> getHostPageData(@PathVariable Integer userNum) {
//        return ResponseEntity.ok(hostPageService.getHostPageData(userNum));
//    }
//
//    @PutMapping("hostinfo/{userNum}")
//    public ResponseEntity<Void> updateHostPageData(@PathVariable Integer userNum, @RequestBody HostPageDTO hostPageDTO) {
//        hostPageService.updateHostPageData(userNum, hostPageDTO);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/rooms/my-rooms/bookings")
    public List<ReservationDTO> getReservations() {
        return reservationService.getReservations();
    }

    @PostMapping("/rooms/my-rooms/bookings/delete")
    public ResponseEntity<Void> deleteBookings(@RequestBody List<Integer> bookingNums) {
        try {
            reservationService.deleteBookingsByIds(bookingNums); // 예약 번호 목록을 이용해 삭제 처리
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @GetMapping("/cancell/list")
    public ResponseEntity<List<ReservationCancellationDTO>> getCancellationList() {
        List<ReservationCancellationDTO> list = hostCancellService.getAllCancellations(); // 모든 취소 데이터 가져오기
        return ResponseEntity.ok(list);
    }
    @GetMapping("/Moneymanager")
    public ResponseEntity<List<SalesDTO>> getMonthlySales() {
        List<SalesDTO> sales = salesService.getMonthlySales();
        return ResponseEntity.ok(sales);
    }

}
