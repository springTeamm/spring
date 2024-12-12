package com.spring.demo.host.service;


import com.spring.demo.entity.*;
import com.spring.demo.host.DTO.HostReplyDTO;
import com.spring.demo.host.DTO.HostReviewDTO;
import com.spring.demo.host.DTO.ReviewReplyDTO;
import com.spring.demo.host.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final HosthostRepository hosthostRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, HostPracticeRoomRepository hostPracticeRoomRepository, HostUserRepository hostUserRepository, HostBookingRepository hostBookingRepository, HostinfohostRepository hostinfohostRepository, HosthostRepository hosthostRepository) {
        this.reviewRepository = reviewRepository;
        this.hostPracticeRoomRepository = hostPracticeRoomRepository;
        this.hostUserRepository = hostUserRepository;
        this.hostBookingRepository = hostBookingRepository;
        this.hostinfohostRepository = hostinfohostRepository;
        this.hosthostRepository = hosthostRepository;
    }

    public List<HostReviewDTO> getAllReviewsForAuthenticatedUser() {
        // 현재 인증된 사용자의 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("사용자가 인증되지 않았습니다.");
        }

        // Spring Security의 UserDetails에서 사용자 ID를 가져옴
        Object principal = authentication.getPrincipal();
        String userId;
        if (principal instanceof UserDetails) {
            userId = ((UserDetails) principal).getUsername();
        } else {
            userId = principal.toString();
        }

        // 사용자 ID로 User 엔티티 조회
        User authenticatedUser = hostUserRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // 해당 사용자가 호스트인지 확인
        boolean isHost = authenticatedUser.getUserRights().equals("HOST");

        // 인증된 사용자와 관련된 리뷰만 조회
        List<PrReview> reviews;
        if (isHost) {
            // 호스트의 경우 자신이 소유한 연습실에 대한 리뷰 조회
            reviews = reviewRepository.findAll().stream()
                    .filter(review -> {
                        PrBooking booking = hostBookingRepository.findByBookingNum(review.getBookingNum())
                                .orElseThrow(() -> new RuntimeException("Booking not found"));
                        PracticeRoom room = hostPracticeRoomRepository.findById(booking.getPrNum())
                                .orElseThrow(() -> new RuntimeException("Practice Room not found"));
                        HostInfo hostInfo = hostinfohostRepository.findByHostInfoNum(room.getHostInfoNum())
                                .orElseThrow(() -> new RuntimeException("HostInfo not found"));

                        // HostInfo의 hostNum으로 호스트 소유권 확인
                        Hosts host = hosthostRepository.findById(hostInfo.getHostNum())
                                .orElseThrow(() -> new RuntimeException("Host not found"));
                        return host.getUserNum().equals(authenticatedUser.getUserNum());
                    })
                    .collect(Collectors.toList());
        } else {
            // 일반 사용자는 자신이 작성한 리뷰만 조회
            reviews = reviewRepository.findAllByUserNum(authenticatedUser.getUserNum());
        }

        // DTO 생성 및 반환
        return reviews.stream().map(review -> {
            // Booking을 통해 PracticeRoom 조회
            PrBooking booking = hostBookingRepository.findByBookingNum(review.getBookingNum())
                    .orElseThrow(() -> new RuntimeException("Booking not found"));

            PracticeRoom room = hostPracticeRoomRepository.findById(booking.getPrNum())
                    .orElseThrow(() -> new RuntimeException("Practice Room not found"));

            // HostInfo에서 hostBisAddress 조회
            HostInfo hostInfo = hostinfohostRepository.findByHostInfoNum(room.getHostInfoNum())
                    .orElseThrow(() -> new RuntimeException("HostInfo not found"));
            String hostAddress = hostInfo.getHostBisAddress();

            return new HostReviewDTO(
                    review.getReviewNum(),
                    hostAddress,    // 연습실 위치
                    room.getPrName(),       // 연습실 이름
                    authenticatedUser.getUserName(),     // 유저 이름
                    review.getReviewStarScore(),
                    review.getTopReviewNum(),
                    review.getReviewText()
            );
        }).collect(Collectors.toList());
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

