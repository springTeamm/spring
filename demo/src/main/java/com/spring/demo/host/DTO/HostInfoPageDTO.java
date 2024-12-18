package com.spring.demo.host.DTO;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HostInfoPageDTO {
    private Integer hostInfoNum;
    private Integer hostNum;
    private String hostBisAddress;
    private String hostBisItem;
    private String hostRegistNum;
    private String hostBisType;
    private String hostCompanyName;
    private String hostCorpName;
    private String hostTaxType;
    private String userId;
    private String userEmail;
    private String userPhone;
    private String representativeName;
    private String userName;
    private String locationName; // 장소 이름 추가
}
