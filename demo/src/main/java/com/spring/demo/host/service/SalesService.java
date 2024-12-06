package com.spring.demo.host.service;


import com.spring.demo.entity.*;
import com.spring.demo.host.DTO.SalesDTO;
import com.spring.demo.host.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class SalesService {

    private final HostAdjustmentRepository adjustmentRepository;
    private final HostPaymentRepository paymentRepository;
    private final HostPrBookingRepository prBookingRepository;
    private final HostPracticeRoomRepository practiceRoomRepository;
    private final HostPrCategoryRepository prCategoryRepository;
    private final HostRefund hostRefund;
    private final HosthostRepository hosthostRepository;
    private final HostUserRepository hostUserRepository;
    @Autowired
    public SalesService(HostAdjustmentRepository adjustmentRepository,
                        HostPaymentRepository paymentRepository,
                        HostPrBookingRepository prBookingRepository,
                        HostPracticeRoomRepository practiceRoomRepository,
                        HostPrCategoryRepository prCategoryRepository, HostRefund hostRefund,
                        HosthostRepository hosthostRepository,
                        HostUserRepository hostUserRepository) {
        this.adjustmentRepository = adjustmentRepository;
        this.paymentRepository = paymentRepository;
        this.prBookingRepository = prBookingRepository;
        this.practiceRoomRepository = practiceRoomRepository;
        this.prCategoryRepository = prCategoryRepository;
        this.hostRefund = hostRefund;
        this.hosthostRepository = hosthostRepository;
        this.hostUserRepository = hostUserRepository;
    }

    public List<SalesDTO> getMonthlySalesForAuthenticatedUser() {
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

        // 호스트와 연관된 연습실 ID 가져오기
        List<Integer> hostRoomIds = practiceRoomRepository.findAllByHostInfo_HostNum(
                hosthostRepository.findByUser_UserNum(authenticatedUser.getUserNum())
                        .orElseThrow(() -> new RuntimeException("Host not found for user: " + userId))
                        .getHostNum()
        ).stream().map(PracticeRoom::getPrNum).collect(Collectors.toList());

        // 매출 데이터를 필터링
        List<Adjustment> adjustments = adjustmentRepository.findAll();

        Map<Integer, Payment> paymentMap = paymentRepository.findAll().stream()
                .filter(payment -> {
                    PrBooking booking = prBookingRepository.findById(payment.getBookingNum()).orElse(null);
                    return booking != null && hostRoomIds.contains(booking.getPrNum());
                })
                .collect(Collectors.toMap(Payment::getPayNum, payment -> payment));

        Map<Integer, PrBooking> bookingMap = prBookingRepository.findAll().stream()
                .filter(booking -> hostRoomIds.contains(booking.getPrNum()))
                .collect(Collectors.toMap(PrBooking::getBookingNum, booking -> booking));

        Map<Integer, PracticeRoom> roomMap = practiceRoomRepository.findAll().stream()
                .filter(room -> hostRoomIds.contains(room.getPrNum()))
                .collect(Collectors.toMap(PracticeRoom::getPrNum, room -> room));

        Map<Integer, String> categoryMap = prCategoryRepository.findAll().stream()
                .collect(Collectors.toMap(PrCategory::getCategoryNum, PrCategory::getCategoryName));

        Map<String, Map<String, Integer>> monthlySales = new HashMap<>();

        for (Adjustment adjustment : adjustments) {
            Payment payment = paymentMap.get(adjustment.getPayNum());
            if (payment == null) continue;

            PrBooking booking = bookingMap.get(payment.getBookingNum());
            if (booking == null) continue;

            PracticeRoom room = roomMap.get(booking.getPrNum());
            if (room == null) continue;

            String categoryName = categoryMap.getOrDefault(room.getCategoryNum(), "Unknown");

            // Extract year-month from adjustment date
            String month = adjustment.getAdDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    .withDayOfMonth(1)
                    .toString();

            // Accumulate sales
            monthlySales.computeIfAbsent(month, k -> new HashMap<>())
                    .merge(categoryName, adjustment.getAdPrice(), Integer::sum);
        }

        List<SalesDTO> salesDTOList = new ArrayList<>();
        int[] runningAccumulatedSales = {0}; // 배열을 사용해 effectively final 상태 유지

        monthlySales.forEach((month, categorySales) -> {
            for (Map.Entry<String, Integer> entry : categorySales.entrySet()) {
                String categoryName = entry.getKey();
                int salesAmount = entry.getValue();

                runningAccumulatedSales[0] += salesAmount; // 배열의 값을 변경

                SalesDTO dto = new SalesDTO();
                dto.setMonth(month);
                dto.setSalesItem(categoryName);
                dto.setSalesAmount(salesAmount);
                dto.setAccumulatedSales(runningAccumulatedSales[0]); // 누적 매출 설정
                dto.setNote("정상");
                salesDTOList.add(dto);
            }
        });

        return salesDTOList;
    }



    @Transactional
    public void deleteRefundByPayNum(Integer payNum) {
        Refund refund = hostRefund.findByPayNum(payNum)
                .orElseThrow(() -> new IllegalArgumentException("해당 payNum에 대한 환불 기록을 찾을 수 없습니다."));
        hostRefund.delete(refund);
    }
}