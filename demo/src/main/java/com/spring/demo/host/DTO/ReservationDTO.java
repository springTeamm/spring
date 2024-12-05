package com.spring.demo.host.DTO;



import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private Integer bookingNum; // 예약 번호 추가
    private Integer prNum;
    private Integer payNum;
    private String userName;
    private Date bookingDate;
    private String prName;
    private Date payDate;
    private Integer payPrice;
    private Integer bookingTotalPerson;
    private String locationName;
    private String userPhone;
    private String prUseable;


}
