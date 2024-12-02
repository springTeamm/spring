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

    @Column(name = "Host_bis_address")
    private String hostBisAddress; // 사업장 주소

    @Column(name = "Host_bis_item")
    private String hostBisItem; // 사업자 등록 관련 업태

    @Column(name = "Host_regist_num")
    private String hostRegistNum; // 사업자 등록 번호

    @Column(name = "Host_bis_type")
    private String hostBisType; // 사업자 업종

    @Column(name = "Host_company_name")
    private String hostCompanyName; // 회사명

    @Column(name = "Host_corp_name")
    private String hostCorpName; // 법인명

    @Column(name = "Host_corepsocial_num")
    private String hostCorpsocialNum; // 법인 사업자 등록 번호

    @Column(name = "Host_open_date")
    private LocalDateTime hostOpenDate; // 사업 개시 일자

    @Column(name = "Host_tax_type")
    private String hostTaxType; // 세금 유형 (과세, 면세 등)

    @Column(name = "Host_regist_date")
    private LocalDateTime hostRegistDate; // 호스트 정보 등록일

    @Column(name = "Host_modify_date")
    private LocalDateTime hostModifyDate; // 정보 수정일

    @Column(name = "Host_delete_date")
    private LocalDateTime hostDeleteDate; // 삭제일 (삭제 예정일자)

    public HostInfo(Hosts host, String hostBisAddress, String hostBisItem, String hostRegistNum,
                    String hostBisType, String hostCompanyName, String hostCorpName,
                    String hostCorpsocialNum, String hostTaxType) {
        this.host = host;
        this.hostBisAddress = hostBisAddress;
        this.hostBisItem = hostBisItem;
        this.hostRegistNum = hostRegistNum;
        this.hostBisType = hostBisType;
        this.hostCompanyName = hostCompanyName;
        this.hostCorpName = hostCorpName;
        this.hostCorpsocialNum = hostCorpsocialNum;
        this.hostTaxType = hostTaxType;
    }
}