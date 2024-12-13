package com.spring.demo.host.controller;


import com.spring.demo.category.service.PracticeRoomService;
import com.spring.demo.entity.Payment;
import com.spring.demo.entity.PrBooking;
import com.spring.demo.entity.PrReview;
import com.spring.demo.entity.Refund;
import com.spring.demo.host.DTO.*;

import com.spring.demo.host.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
    @PutMapping("/spacelist/updateRoom")
    public ResponseEntity<String> updateRoom(@RequestBody Map<String, Object> requestData) {
        try {
            // 데이터 추출
            Integer prNum = Integer.parseInt(requestData.get("roomNumber").toString());
            String roomName = (String) requestData.get("roomName");
            String prUseable = (String) requestData.get("prUseable");
            String locationName = (String) requestData.get("locationName");
            Integer rentalPrice = (Integer) requestData.get("rentalPrice");
            Integer discountPrice = (Integer) requestData.get("discountPrice");
            String displayStatus = (String) requestData.get("displayStatus");
            LocalDateTime lastModifiedDate = LocalDateTime.parse((String) requestData.get("lastModifiedDate"));

            // 서비스 호출
            spaceSelectService.updateRoomByPrNum(prNum, roomName, prUseable, locationName, rentalPrice, discountPrice, displayStatus, lastModifiedDate);

            return ResponseEntity.ok("방 정보 수정 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("방 정보 수정 실패: " + e.getMessage());
        }
    }

    @PostMapping("/users/{userNum}/rooms/{prNum}/booking")
    public ResponseEntity<PrBooking> addBooking(
            @PathVariable Integer userNum,
            @PathVariable Integer prNum,
            @RequestBody Map<String, Object> requestBody
    ) {
        // PrBooking 데이터 설정
        PrBooking bookingData = new PrBooking();
        bookingData.setUserNum(userNum);
        bookingData.setPrNum(prNum);
        bookingData.setBookingTotalPerson((Integer) requestBody.get("bookingTotalPerson"));
        bookingData.setBookingTotalPrice((Integer) requestBody.get("bookingTotalPrice"));
        bookingData.setBookingUsingTime((Integer) requestBody.get("bookingUsingTime"));
        bookingData.setBookingPaymentMethod((String) requestBody.get("bookingPaymentMethod"));

        // Payment 데이터 설정
        Integer payPrice = (Integer) requestBody.get("payPrice");
        String payStatus = (String) requestBody.get("payStatus");

        // 예약 및 결제 저장
        PrBooking savedBooking = reservationService.addBookingWithPayment(bookingData, payPrice, payStatus);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);
    }

    @PostMapping("/approve")
    public ResponseEntity<String> approveCancellations(@RequestBody Map<String, Object> requestData) {
        try {
            // payNums 추출
            List<Integer> payNums = (List<Integer>) requestData.get("payNums");

            // refundDate 추출 및 변환
            String refundDateString = (String) requestData.get("refundDate");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date refundDate = dateFormat.parse(refundDateString);

            // 서비스 호출
            hostCancellService.approveCancellations(payNums, refundDate);

            return ResponseEntity.ok("취소가 승인되고 환불 데이터가 생성되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("취소 처리 중 오류 발생: " + e.getMessage());
        }
    }

}
