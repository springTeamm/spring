package com.spring.demo.host.controller;


import com.spring.demo.entity.PrBooking;
import com.spring.demo.host.DTO.*;
import com.spring.demo.host.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @Autowired
    private SpaceSelectService hostspaceSelectService;
    @Autowired
    private SpaceSelectService spaceSelectService;

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
        List<HostReviewDTO> reviews = reviewService.getAllReviewsForAuthenticatedUser();
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

    @GetMapping("/review/{reviewNum}/replies")
    public List<ReviewReplyDTO> getReplies(@PathVariable Integer reviewNum) {
        return reviewService.getRepliesByTopReviewNum(reviewNum);
    }

    // 공통 답글 등록
    @PostMapping("/reply")
    public ResponseEntity<String> addCommonReply(@RequestBody List<HostReplyDTO> replies) {
        reviewService.addCommonReplies(replies);
        return ResponseEntity.ok("공통 답글이 등록되었습니다.");
    }

    @GetMapping("/rooms/my-rooms/bookings")
    public List<ReservationDTO> getReservations() {
        return reservationService.getReservationsForAuthenticatedUser();
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
        List<ReservationCancellationDTO> list = hostCancellService.getCancellationsForAuthenticatedHost(); // 모든 취소 데이터 가져오기
        return ResponseEntity.ok(list);
    }

    @GetMapping("/Moneymanager")
    public ResponseEntity<List<SalesDTO>> getMonthlySales() {
        List<SalesDTO> sales = salesService.getMonthlySalesForAuthenticatedUser();
        return ResponseEntity.ok(sales);
    }

    // 매출 데이터 삭제
    @PostMapping("/Moneymanager/delete")
    public ResponseEntity<String> deleteRefund(@RequestBody Map<String, Integer> payload) {
        Integer payNum = payload.get("payNum");
        if (payNum == null) {
            return ResponseEntity.badRequest().body("payNum 값이 누락되었습니다.");
        }

        salesService.deleteRefundByPayNum(payNum);
        return ResponseEntity.ok("해당 payNum에 대한 환불이 삭제되었습니다.");
    }


    @GetMapping("/spacelist")
    public List<HostPracticeRoomDTO> getPracticeRooms() {
        return hostspaceSelectService.getPracticeRoomsForAuthenticatedUser();
    }

    @PostMapping("/spacelist/deleteRooms")
    public ResponseEntity<String> deleteRooms(@RequestBody Map<String, List<Integer>> payload) {
        List<Integer> roomIds = payload.get("roomIds"); // "roomIds" 키로 배열 추출
        hostspaceSelectService.deleteRooms(roomIds);
        return ResponseEntity.ok("삭제 완료");

    }
    @PostMapping("/updateRoom")
    public ResponseEntity<String> updateRoom(
            @RequestParam Integer prNum,
            @RequestParam String roomName,
            @RequestParam String prUseable,
            @RequestParam String locationName,
            @RequestParam Integer rentalPrice,
            @RequestParam Integer discountPrice,
            @RequestParam String displayStatus,
            @RequestParam String lastModifiedDate) {
        try {
            // prNum을 기준으로 데이터 수정
            LocalDateTime modifiedDate = LocalDateTime.parse(lastModifiedDate);
            spaceSelectService.updateRoomByPrNum(prNum, roomName, prUseable, locationName, rentalPrice, discountPrice, displayStatus, modifiedDate);
            return ResponseEntity.ok("방 정보가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("방 정보 수정 중 오류 발생: " + e.getMessage());
        }
    }


    @GetMapping("/hostinfo")
    public ResponseEntity<HostInfoPageDTO> getHostInfo(@RequestParam(required = false) Integer hostNum) {
        // hostNum이 없으면 기본값으로 1 사용
        if (hostNum == null) {
            hostNum = 1;
        }
        HostInfoPageDTO hostInfo = hostInfoPageService.getHostInfoForAuthenticatedUser();
        return ResponseEntity.ok(hostInfo);
    }

    //예약 추가
    @PostMapping("/users/{userNum}/rooms/{prNum}/booking")
    public ResponseEntity<PrBooking> addBooking(
            @PathVariable Integer userNum,  // URL 경로에서 값 받기
            @PathVariable Integer prNum,
            @RequestBody PrBooking bookingData
    ) {
        bookingData.setUserNum(userNum);
        bookingData.setPrNum(prNum);

        PrBooking savedBooking = reservationService.addBooking(bookingData);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);
    }




}
