package com.spring.demo.security.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "host_info")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class HostInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Host_info_num")
    private Integer hostInfoNum; // 호스트 정보 고유 번호 (PK)

    @OneToOne
    @JoinColumn(name = "Host_num")
    private Hosts host; // Hosts 엔티티와 1:1 관계 (각 호스트의 추가 정보를 담는 엔티티)

    @Column(name = "Host_Bis_Address", nullable = false)
    private String businessAddress; // 사업장 주소

    @Column(name = "Host_Bis_Item", nullable = false)
    private String businessItem; // 업태

    @Column(name = "Host_Regist_Num")
    private String hostRegistNum;  // 사업자 등록 번호

    @Column(name = "Host_Bis_Type", nullable = false)
    private String businessType; // 업종

    @Column(name = "Host_Company_Name", nullable = false)
    private String companyName; // 상호명

    @Column(name = "Host_Corp_Name")
    private String corporateName; // 법인명

    @Column(name = "Host_Corepsocial_Num")
    private String corporateSocialNum; // 법인 사회 번호

    @Column(name = "Host_open_Date")
    private LocalDateTime hostOpenDate; // 개업일

    @Column(name = "Host_Tax_Type")
    private String hostTaxType; // 세금 유형

    @Column(name = "Host_regist_date")
    private LocalDateTime hostRegistDate; // 호스트 정보 등록일

    @Column(name = "Host_modify_date")
    private LocalDateTime hostModifyDate; // 정보 수정일

    @Column(name = "Host_delete_date")
    private LocalDateTime hostDeleteDate; // 삭제일 (삭제 예정일자)

    public HostInfo(Hosts host,
                    String businessAddress,
                    String businessItem,
                    String hostRegistNum,
                    String businessType,
                    String companyName,
                    String corporateName,
                    String corporateSocialNum,
                    String hostTaxType,
                    LocalDateTime hostOpenDate,
                    LocalDateTime hostRegistDate) {
        this.host = host;
        this.businessAddress = businessAddress;
        this.businessItem = businessItem;
        this.hostRegistNum = hostRegistNum;
        this.businessType = businessType;
        this.companyName = companyName;
        this.corporateName = corporateName;
        this.corporateSocialNum = corporateSocialNum;
        this.hostTaxType = hostTaxType;
        this.hostOpenDate = hostOpenDate;
        this.hostRegistDate = hostRegistDate;
    }
}