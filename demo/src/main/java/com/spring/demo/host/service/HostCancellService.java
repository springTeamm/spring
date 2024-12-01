package com.spring.demo.host.service;

import com.spring.demo.category.repository.PrBookingRepository;
import com.spring.demo.entity.*;
import com.spring.demo.host.DTO.ReservationCancellationDTO;
import com.spring.demo.host.DTO.RoomDetailsDTO;
import com.spring.demo.host.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HostCancellService {

    @Autowired
    private HostPracticeRoomRepository hostPracticeRoomRepository;
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
    private PrBookingRepository prBookingRepository;
    public List<ReservationCancellationDTO> getAllCancellations() {
        // Native Query 결과 가져오기
        List<Object[]> rawResults = hostpaymentRepository.findAllWithDetails();

        // DTO로 매핑
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
