package com.spring.demo.host.DTO;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoomDetailsDTO {
    private Integer prNum;         // 방 번호
    private Integer Paynum;
    private String prName;         // 방 이름
    private Date payDate; // 결제일시
    private Date refundDate; // 환불일시
}
