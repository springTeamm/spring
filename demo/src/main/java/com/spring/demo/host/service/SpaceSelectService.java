package com.spring.demo.host.service;

import com.spring.demo.entity.PrDetail;
import com.spring.demo.entity.PracticeRoom;
import com.spring.demo.entity.User;
import com.spring.demo.host.DTO.HostPracticeRoomDTO;
import com.spring.demo.host.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpaceSelectService {
    @Autowired
    private HostPracticeRoomRepository hostpracticeRoomRepository;

    @Autowired
    private HostPrDetailRepository hostprDetailRepository;

    @Autowired
    private HostPrJpgRepository hostprJpgRepository;

    @Autowired
    private HostPrCategoryRepository hostprCategoryRepository;

    @Autowired
    private HostPrShareDeviceRepository prShareDeviceRepository;

    @Autowired
    private HostPrDetailRepository prDetailRepository;

    @Autowired
    private HostAdjustmentRepository adjustmentRepository;

    @Autowired
    private HostRefund refundRepository;

    @Autowired
    private HostPaymentRepository paymentRepository;

    @Autowired
    private ReviewRepository prReviewRepository;

    @Autowired
    private HostPrBookingRepository prBookingRepository;

    @Autowired
    private HostPracticeRoomRepository practiceRoomRepository;

    @Autowired
    private HostUserRepository hostUserRepository;

    @Autowired
    private HosthostRepository hosthostRepository;

    public List<HostPracticeRoomDTO> getPracticeRoomsForAuthenticatedUser() {
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

        // 사용자가 호스트인지 확인
        boolean isHost = authenticatedUser.getUserRights().equals("HOST");
        if (!isHost) {
            throw new RuntimeException("권한이 없습니다.");
        }

        // 호스트와 연관된 연습실만 조회
        List<PracticeRoom> practiceRooms = hostpracticeRoomRepository.findAllByHostInfo_HostNum(
                hosthostRepository.findByUser_UserNum(authenticatedUser.getUserNum())
                        .orElseThrow(() -> new RuntimeException("Host not found for user: " + userId))
                        .getHostNum()
        );

        // DTO 생성 및 반환
        return practiceRooms.stream().map(practiceRoom -> {
            // PrDetail 조회
            PrDetail prDetail = hostprDetailRepository.findById(practiceRoom.getPrNum())
                    .orElseThrow(() -> new RuntimeException("PrDetail not found for PracticeRoom ID: " + practiceRoom.getPrNum()));

            return new HostPracticeRoomDTO(
                    practiceRoom.getPrNum(),                             // Integer roomNumber
                    practiceRoom.getLocationName(),                      // String roomName
                    prDetail.getPrPrice(),                               // Integer rentalPrice
                    prDetail.getPrPrice() - prDetail.getPrDiscountPrice(), // Integer discountPrice
                    prDetail.getPrDiscountPrice(),                       // Integer sellerDiscountPrice
                    prDetail.getPrDisplayStatus(),                       // String displayStatus
                    prDetail.getPrRegisteredDate(),                      // LocalDateTime registeredDate
                    prDetail.getPrLastModifiedDate()                     // LocalDateTime lastModifiedDate
            );
        }).collect(Collectors.toList());
    }

    @Transactional
    public void deleteRooms(List<Integer> roomIds) {
        roomIds.forEach(roomId -> {
            // 1. 연습실 사진(pr_jpg) 삭제
            hostprJpgRepository.deleteByPrNum(roomId);


            // 3. 공유 물품 목록(pr_share_device) 삭제
            prShareDeviceRepository.deleteByPrNum(roomId);

            // 4. 상세정보 테이블(pr_detail) 삭제
            prDetailRepository.deleteByPrNum(roomId);

            // 5. 정산 테이블(adjustment) 삭제
            adjustmentRepository.deleteByPayNum(roomId);

            // 6. 환불 테이블(refund) 삭제
            refundRepository.deleteByPayNum(roomId);

            // 7. 결제 테이블(payment) 삭제
            paymentRepository.deleteByBookingNum(roomId);

            // 8. 리뷰 테이블(pr_review) 삭제
            prReviewRepository.deleteByBookingNum(roomId);

            // 9. 예약 테이블(pr_booking) 삭제
            prBookingRepository.deleteByBookingNum(roomId);

            // 10. 연습실 테이블(practice_room) 삭제
            practiceRoomRepository.deleteById(roomId);
        });
    }

}
