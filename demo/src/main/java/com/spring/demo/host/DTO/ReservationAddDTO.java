package com.spring.demo.host.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ReservationAddDTO {
    private Integer userNum;
    private Integer roomNum;
    private String startDate;
    private String endDate;
    private String userName;
    private String userPhone;
    private Integer totalPrice;
}
