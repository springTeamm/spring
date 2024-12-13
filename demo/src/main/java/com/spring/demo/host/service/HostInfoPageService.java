package com.spring.demo.host.service;

import com.spring.demo.entity.HostInfo;
import com.spring.demo.entity.PracticeRoom;
import com.spring.demo.entity.User;

import com.spring.demo.host.DTO.HostInfoPageDTO;
import com.spring.demo.host.repository.HostPracticeRoomRepository;
import com.spring.demo.host.repository.HostUserRepository;
import com.spring.demo.host.repository.HostinfohostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HostInfoPageService {
    private final HostinfohostRepository hostInfoHostRepository;
    private final HostUserRepository hostUserRepository;
    private final HostPracticeRoomRepository practiceRoomRepository;
    // 명시적인 생성자 작성
    public HostInfoPageService(HostinfohostRepository hostInfoHostRepository, HostUserRepository hostUserRepository, HostPracticeRoomRepository practiceRoomRepository) {
        this.hostInfoHostRepository = hostInfoHostRepository;
        this.hostUserRepository = hostUserRepository;
        this.practiceRoomRepository = practiceRoomRepository;
    }


    public HostInfoPageDTO getHostInfoByHostNum(Integer hostNum) {
        HostInfo hostInfo = hostInfoHostRepository.findByHostNum(hostNum)
                .orElseThrow(() -> new RuntimeException("Host info not found"));

        User user = hostUserRepository.findById(hostInfo.getHostNum())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // PracticeRoom에서 장소 이름 가져오기
        List<PracticeRoom> practiceRooms = practiceRoomRepository.findByHostInfoNum(hostInfo.getHostInfoNum());
        String locationName = practiceRooms.isEmpty() ? "정보 없음" : practiceRooms.get(0).getLocationName();

        HostInfoPageDTO dto = new HostInfoPageDTO();
        dto.setHostInfoNum(hostInfo.getHostInfoNum());
        dto.setHostNum(hostInfo.getHostNum());
        dto.setHostBisAddress(hostInfo.getHostBisAddress());
        dto.setHostBisItem(hostInfo.getHostBisItem());
        dto.setHostRegistNum(hostInfo.getHostRegistNum());
        dto.setHostBisType(hostInfo.getHostBisType());
        dto.setHostCompanyName(hostInfo.getHostCompanyName());
        dto.setHostCorpName(hostInfo.getHostCorpName());
        dto.setHostTaxType(hostInfo.getHostTaxType());
        dto.setUserId(user.getUserId());
        dto.setUserEmail(user.getUserEmail());
        dto.setUserPhone(user.getUserPhone());
        dto.setRepresentativeName(user.getUserName());
        dto.setUserName(user.getUserName());
        dto.setLocationName(locationName); // 장소 이름 설정

        return dto;
    }
}
