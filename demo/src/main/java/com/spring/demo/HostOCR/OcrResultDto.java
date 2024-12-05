package com.spring.demo.HostOCR;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class OcrResultDto {
    private String hostRegistNum;
    private String hostCompanyName;
    private String hostCorpName;
    private String hostBisItem;
    private String hostBisType;
    private Date hostOpenDate;
    private String hostCorpsocialNum;
    private String hostTaxType;

    private String hostBisAddress;




}