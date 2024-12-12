package com.spring.demo.host.service;


import com.spring.demo.entity.HostInfo;
import com.spring.demo.entity.User;
import com.spring.demo.host.DTO.HostInfoPageDTO;
import com.spring.demo.host.repository.HostUserRepository;
import com.spring.demo.host.repository.HostinfohostRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class HostInfoPageService {
    private final HostinfohostRepository hostInfoHostRepository;
    private final HostUserRepository hostUserRepository;

    // 명시적인 생성자 작성
    public HostInfoPageService(HostinfohostRepository hostInfoHostRepository, HostUserRepository hostUserRepository) {
        this.hostInfoHostRepository = hostInfoHostRepository;
        this.hostUserRepository = hostUserRepository;
    }


    public HostInfoPageDTO getHostInfoForAuthenticatedUser() {
        // 현재 인증된 사용자의 정보를 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("사용자가 인증되지 않았습니다.");
        }

        // Spring Security의 UserDetails에서 사용자 ID를 가져옴
        Object principal = authentication.getPrincipal();
        String userId;
        if (principal instanceof UserDetails) {
            userId = ((UserDetails) principal).getUsername(); // 여기서 userId는 Spring Security UserDetails의 username
        } else {
            userId = principal.toString(); // 기본적으로 Principal을 문자열로 사용
        }

        // 사용자 ID로 User 엔티티 조회
        User user = hostUserRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // 사용자와 연결된 HostInfo 조회
        HostInfo hostInfo = hostInfoHostRepository.findByHostNum(user.getUserNum())
                .orElseThrow(() -> new RuntimeException("Host info not found for user ID: " + userId));

        // DTO로 변환하여 반환
        HostInfoPageDTO dto = new HostInfoPageDTO();
        dto.setHostInfoNum(hostInfo.getHostInfoNum());
        dto.setHostNum(hostInfo.getHostNum());
        dto.setHostBisAddress(hostInfo.getHostBisAddress());
        dto.setHostBisItem(hostInfo.getHostBisItem());
        dto.setHostRegistNum(hostInfo.getHostRegistNum());
        dto.setHostBisType(hostInfo.getHostBisType());
        dto.setHostCompanyName(hostInfo.getHostCompanyName());
        dto.setHostCorpName(hostInfo.getHostCorpName());
        dto.setUserId(user.getUserId());
        dto.setUserEmail(user.getUserEmail());
        dto.setUserPhone(user.getUserPhone());
        dto.setRepresentativeName(user.getUserName());
        dto.setHostTaxType(hostInfo.getHostTaxType());
        dto.setUserName(user.getUserName());

        return dto;
    }

}
