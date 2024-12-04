package com.spring.demo.admin.userManager.service;

import com.spring.demo.admin.userManager.dto.UserManagerDTO;
import com.spring.demo.admin.userManager.entity.*;
import com.spring.demo.admin.userManager.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserManagerService {

    @Autowired
    private UserManagerRepository userManagerRepository;
    @Autowired
    private PrBookingRepository prBookingRepository;
    @Autowired
    private HostsRepository hostsRepository;
    @Autowired
    private HostInfoRepository hostInfoRepository;
    @Autowired
    private PracticeRoomRepository practiceRoomRepository;

    @Autowired
    private ModelMapper modelMapper;



    public List<UserManagerDTO> getUserState() {
        List<User> users = userManagerRepository.findAll();

        List<UserManagerDTO> userManagerDTOList = new ArrayList<>();

        for(User user : users) {
            // UserNum - > userMakingTIme까지 들어가 있음
            UserManagerDTO dto = modelMapper.map(user, UserManagerDTO.class);

            //이용횟수 찾기
            List<PrBooking> usingList = prBookingRepository.findByUserNum(dto.getUserNum());
            int usingNum = 0;
            for(PrBooking prBooking : usingList) {
                usingNum ++;
            }
            dto.setUsingNum(usingNum);

            //예약 진행 여부 확인
            LocalDateTime now = LocalDateTime.now();
            List<PrBooking> bookingList = prBookingRepository.findByUserNumOrderByBookingDate(dto.getUserNum());
            if(bookingList.isEmpty()) {
                dto.setRegistState("예약 없음");
            } else{
                for(PrBooking prBooking : bookingList) {
                    if(prBooking.getBookingDate().isAfter(now)){
                        dto.setRegistState("예약 중");
                    } else{
                        dto.setRegistState("예약 끝남");
                    }
                }
            }

            //등록된 방 수 파악
            int roomNum = 0;
            Hosts isHost = hostsRepository.findByUserNum(dto.getUserNum());
            if(isHost == null) {
                dto.setRegistRoom(null);
            } else {
                List<HostInfo> infoList = hostInfoRepository.findByHostNum(isHost.getHostNum());
                for(HostInfo hostInfo : infoList) {
                    List<PracticeRoom> roomList = practiceRoomRepository.findByHostInfoNum(hostInfo.getHostInfoNum());
                    for(PracticeRoom practiceRoom : roomList) {
                        roomNum ++;
                    }
                }
            }
            dto.setRegistRoom(roomNum);



            userManagerDTOList.add(dto);
        }

        return userManagerDTOList;
    }
}
