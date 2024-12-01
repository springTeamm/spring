package com.spring.demo.host.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCancellationDTO {
    private Integer payNum;          // 결제 번호
    private Integer bookingNum;      // 예약 번호
    private Integer payPrice;        // 대여 가격
    private Date payDate;            // 결제 일시
    private Date bookingCancel;      // 취소 일시
    private Date bookingDate;        // 대여 시간
    private Integer userNum;         // 유저 번호
    private Integer prNum;           // 방 번호
    private Date refundDate;         // 환불 일시
    private Integer refundPrice;     // 환불 가격
    private String prName;           // 방 이름
    private String locationName;     // 위치 이름
    private String userName;         // 대여자명
}