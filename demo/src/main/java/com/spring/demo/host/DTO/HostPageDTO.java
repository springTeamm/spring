package com.spring.demo.host.DTO;

import com.spring.demo.entity.*;
import lombok.Data;

import java.util.List;

@Data
public class HostPageDTO {
    private User userInfo; // 유저 정보
    private List<HostInfo> hostInfos; // 여러 개의 호스트 정보
    private List<Payment> payments; // 정산 정보
    private List<Refund> refunds;
    private List<PracticeRoom> practiceRooms;
}
