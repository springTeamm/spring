package com.spring.demo.host.controller;


import com.spring.demo.category.service.PracticeRoomService;
import com.spring.demo.entity.PrReview;
import com.spring.demo.host.DTO.*;

import com.spring.demo.host.service.*;

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
    private final HostInfoPageService hostInfoPageService;

    @Autowired
    private HostCancellService hostCancellService;
//    private final HostPageService hostPageService;

    @Autowired
    private SpaceSelectService hostspaceSelectService;
    @Autowired
    public hostcontroller(ReviewService reviewService, ReservationService reservationService, HostCancellService hostcancellService
    , SalesService salesService, HostInfoPageService hostInfoPageService) {
        this.reviewService = reviewService;
//        this.hostPageService = hostPageService;
        this.reservationService = reservationService;
        this.hostcancellService = hostcancellService;
        this.salesService = salesService;
        this.hostInfoPageService = hostInfoPageService;
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

    @PostMapping("/rooms/my-rooms/bookings/cancel")

    public ResponseEntity<?> cancelBookings(@RequestBody Map<String, List<Integer>> request) {
        List<Integer> bookingNums = request.get("bookingNums");
        if (bookingNums == null || bookingNums.isEmpty()) {
            return ResponseEntity.badRequest().build(); // 잘못된 요청 처리
        }
        reservationService.deleteBookingsByIds(bookingNums);

        return ResponseEntity.ok().build();
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

    @GetMapping("/spacelist")
    public List<HostPracticeRoomDTO> getPracticeRooms() {
        return hostspaceSelectService.getPracticeRooms();
    }
    @PostMapping("/spacelist/deleteRooms")
    public ResponseEntity<String> deleteRooms(@RequestBody Map<String, List<Integer>> payload) {
        List<Integer> roomIds = payload.get("roomIds"); // "roomIds" 키로 배열 추출
        hostspaceSelectService.deleteRooms(roomIds);
        return ResponseEntity.ok("삭제 완료");

    }
    @GetMapping("/hostinfo")
    public ResponseEntity<HostInfoPageDTO> getHostInfo(@RequestParam(required = false) Integer hostNum) {
        // hostNum이 없으면 기본값으로 1 사용
        if (hostNum == null) {
            hostNum = 1; // 임시 값
        }
        HostInfoPageDTO hostInfo = hostInfoPageService.getHostInfoByHostNum(hostNum);
        return ResponseEntity.ok(hostInfo);
    }
}
