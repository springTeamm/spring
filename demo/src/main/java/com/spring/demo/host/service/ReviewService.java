package com.spring.demo.host.service;

import com.spring.demo.entity.*;
import com.spring.demo.host.DTO.HostReplyDTO;
import com.spring.demo.host.DTO.HostReviewDTO;
import com.spring.demo.host.DTO.ReviewReplyDTO;
import com.spring.demo.host.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final HostPracticeRoomRepository hostPracticeRoomRepository;
    private final HostUserRepository hostUserRepository;
    private final HostBookingRepository hostBookingRepository;
    private final HostinfohostRepository hostinfohostRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, HostPracticeRoomRepository hostPracticeRoomRepository, HostUserRepository hostUserRepository, HostBookingRepository hostBookingRepository, HostinfohostRepository hostinfohostRepository) {
        this.reviewRepository = reviewRepository;
        this.hostPracticeRoomRepository = hostPracticeRoomRepository;
        this.hostUserRepository = hostUserRepository;
        this.hostBookingRepository = hostBookingRepository;
        this.hostinfohostRepository = hostinfohostRepository;
    }

    // 리뷰 목록 조회
    public List<HostReviewDTO> getAllReviews() {
        List<PrReview> reviews = reviewRepository.findAll();
        List<HostReviewDTO> reviewDTOs = new ArrayList<>();

        for (PrReview review : reviews) {
            // Booking을 통해 PracticeRoom 조회
            PrBooking booking = hostBookingRepository.findByBookingNum(review.getBookingNum())
                    .orElseThrow(() -> new RuntimeException("Booking not found"));

            PracticeRoom room = hostPracticeRoomRepository.findById(booking.getPrNum())
                    .orElseThrow(() -> new RuntimeException("Practice Room not found"));

            // User 조회
            User user = hostUserRepository.findById(review.getUserNum())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // HostInfo에서 hostBisAddress 조회
            HostInfo hostInfo = hostinfohostRepository.findByHostInfoNum(room.getHostInfoNum());
            String hostAddress = hostInfo != null ? hostInfo.getHostBisAddress() : "주소 없음";

            // DTO 생성
            HostReviewDTO dto = new HostReviewDTO(
                    review.getReviewNum(),
                    hostAddress,    // 연습실 위치
                    room.getPrName(),       // 연습실 이름
                    user.getUserName(),     // 유저 이름
                    review.getReviewStarScore(),
                    review.getTopReviewNum(),
                    review.getReviewText()
            );

            reviewDTOs.add(dto);
        }
        return reviewDTOs;
    }


    // 개별 답글 추가
    // 리뷰 답글 추가
    @Transactional
    public void addReply(Integer reviewId, String replyText) {
        // 상위 리뷰 조회
        PrReview parentReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("상위 리뷰를 찾을 수 없습니다."));

        // 새로운 답글 생성
        PrReview replyReview = new PrReview();
        replyReview.setBookingNum(parentReview.getBookingNum());
        replyReview.setUserNum(parentReview.getUserNum()); // 작성자 정보 복사
        replyReview.setReviewDate(new Date());
        replyReview.setReviewText(replyText);
        replyReview.setReviewStarScore(0); // 답글에는 별점이 필요 없으므로 기본값 설정
        replyReview.setTopReviewNum(parentReview.getReviewNum()); // 상위 리뷰 번호 저장

        // 리뷰 저장
        reviewRepository.save(replyReview);
    }

    // 공통 답글 추가
    public void addCommonReplies(List<HostReplyDTO> replies) {

        for (HostReplyDTO dto : replies) {
            // 상위 리뷰 조회
            PrReview parentReview = reviewRepository.findById(dto.getReviewNum())
                    .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

            // 새로운 답글 생성
            PrReview reply = new PrReview();
            reply.setTopReviewNum(parentReview.getReviewNum()); // 상위 댓글 번호 설정
            reply.setReviewText(dto.getReplyText()); // 답글 내용 설정
            reply.setReviewDate(new Date()); // 답글 작성 날짜 설정
            reply.setUserNum(parentReview.getUserNum()); // 동일 작성자
            reply.setBookingNum(parentReview.getBookingNum()); // 동일 예약 번호
            reply.setReviewStarScore(0); // 답글에는 별점 없음 (기본값 0 설정)

            // 새로운 답글 저장
            reviewRepository.save(reply);
        }
    }


    // 리뷰 신고
    public void reportReview(Integer reviewId, String reason) {
        PrReview review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("해당 리뷰를 찾을 수 없습니다."));
        // 신고 처리 로직 구현 (예: 별도의 신고 테이블에 저장)
        // reportRepository.save(new Report(reviewId, reason));
    }

    public List<ReviewReplyDTO> getRepliesByTopReviewNum(Integer topReviewNum) {
        List<PrReview> replies = reviewRepository.findByTopReviewNum(topReviewNum);

        return replies.stream()
                .map(reply -> {
                    String userName = hostUserRepository.findByUserNum(reply.getUserNum())
                            .map(User::getUserName)
                            .orElse("Unknown User");
                    return new ReviewReplyDTO(
                            reply.getReviewNum(),
                            reply.getReviewText(),
                            userName

                    );
                })
                .collect(Collectors.toList());
    }
}

