package com.spring.demo.host.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesDTO {
    private String month;          // 월
    private String salesItem;      // 매출 항목
    private Integer salesAmount;   // 매출액 (원 단위)
    private Integer accumulatedSales; // 누적 매출액 (원 단위)
    private String note;// 비고
    private Integer payNum;
}
