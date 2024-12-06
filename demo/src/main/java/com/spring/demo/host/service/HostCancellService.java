package com.spring.demo.host.service;

import com.spring.demo.entity.*;
import com.spring.demo.host.DTO.ReservationCancellationDTO;
import com.spring.demo.host.DTO.RoomDetailsDTO;
import com.spring.demo.host.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HostCancellService {

    @Autowired
    private HostPracticeRoomRepository hostpracticeRoomRepository;
    @Autowired
    private HostPaymentRepository hostpaymentRepository;
    @Autowired
    private HostPaymentDateRepository hostPaymentDateRepository;
    @Autowired
    private HostRefund hostRefund;
    @Autowired
    private HostPrBookingRepository hostPrBookingRepository;
    @Autowired
    private HostUserRepository hostUserRepository;
    @Autowired
    private HosthostRepository hosthostRepository;
    @Transactional
    public List<ReservationCancellationDTO> getCancellationsForAuthenticatedHost() {
        // 현재 인증된 사용자 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("사용자가 인증되지 않았습니다.");
        }

        // Spring Security의 UserDetails에서 사용자 ID를 가져옴
        Object principal = authentication.getPrincipal();
        String userId = (principal instanceof UserDetails) ?
                ((UserDetails) principal).getUsername() : principal.toString();

        // 사용자 엔티티 조회
        User authenticatedUser = hostUserRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 사용자가 호스트인지 확인
        if (!authenticatedUser.getUserRights().equals("HOST")) {
            throw new RuntimeException("권한이 없습니다.");
        }

        // 호스트와 연관된 연습실 ID 가져오기
        Hosts host = hosthostRepository.findByUser_UserNum(authenticatedUser.getUserNum())
                .orElseThrow(() -> new RuntimeException("Host not found for user: " + userId));

        List<Integer> hostRoomIds = hostpracticeRoomRepository.findAllByHostInfo_HostNum(host.getHostNum())
                .stream()
                .map(PracticeRoom::getPrNum)
                .collect(Collectors.toList());

        // 연습실 ID를 사용하여 관련 데이터 필터링
        List<Object[]> rawResults = hostpaymentRepository.findAllWithDetails().stream()
                .filter(result -> hostRoomIds.contains((Integer) result[7])) // result[7]: prNum
                .collect(Collectors.toList());

        // DTO 변환
        List<ReservationCancellationDTO> cancellations = new ArrayList<>();
        for (Object[] result : rawResults) {
            ReservationCancellationDTO dto = new ReservationCancellationDTO();
            dto.setPayNum((Integer) result[0]);
            dto.setBookingNum((Integer) result[1]);
            dto.setPayPrice((Integer) result[2]);
            dto.setPayDate((Date) result[3]);
            dto.setBookingCancel((Date) result[4]);
            dto.setBookingDate((Date) result[5]);
            dto.setUserNum((Integer) result[6]);
            dto.setPrNum((Integer) result[7]);
            dto.setRefundDate((Date) result[8]);
            dto.setRefundPrice((Integer) result[9]);
            dto.setPrName((String) result[10]);
            dto.setLocationName((String) result[11]);
            dto.setUserName((String) result[12]);

            cancellations.add(dto);
        }

        return cancellations;
    }

}
