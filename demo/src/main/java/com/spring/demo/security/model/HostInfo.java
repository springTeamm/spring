package com.spring.demo.security.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "host_info")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class HostInfo {
    @Id
    @GeneratedValue
    @Column(name = "Host_info_num")
    private Integer hostInfoNum;

    @Column(name = "Host_num")
    private Integer hostNum;

    @Column(name = "Host_bis_address") //사업장 주소 정보
    private String hostBisAddress;

    @Column(name = "Host_bis_item") //사업장 소재지 정보
    private String hostBisItem;

    @Column(name = "Host_regist_num") //사업자 등록 번호
    private String hostRegistNum;

    @Column(name = "Host_bis_type") //사업자 업종 정보
    private String hostBisType;

    @Column(name = "Host_company_name") //기업 이름
    private String hostCompanyName;

    @Column(name = "Host_corp_name") //법인 이름
    private String hostCorpName;

    @Column(name = "Host_corepsocial_num") // 법인 사업자 등록번호
    private String hostCorpsocialNum;

    @Column(name = "Host_open_date") //발행 일자 정보
    private Date hostOpenDate;

    @Column(name = "Host_tax_type") //과세 유형 정보
    private String hostTaxType;

    @Column(name = "Host_regist_date") //등록일
    private Date hostRegistDate;

    @Column(name = "Host_modify_date") //수정일
    private Date hostModifyDate;

    @Column(name = "Host_delete_date") //삭제일
    private Date hostDeleteDate;

    public HostInfo(Integer hostInfoNum, Integer hostNum, String hostBisAddress, String hostBisItem, String hostRegistNum, String hostBisType, String hostCompanyName, String hostCorpName, String hostCorpsocialNum, Date hostOpenDate, String hostTaxType, Date hostRegistDate, Date hostModifyDate, Date hostDeleteDate) {
        this.hostInfoNum = hostInfoNum;
        this.hostNum = hostNum;
        this.hostBisAddress = hostBisAddress;
        this.hostBisItem = hostBisItem;
        this.hostRegistNum = hostRegistNum;
        this.hostBisType = hostBisType;
        this.hostCompanyName = hostCompanyName;
        this.hostCorpName = hostCorpName;
        this.hostCorpsocialNum = hostCorpsocialNum;
        this.hostOpenDate = hostOpenDate;
        this.hostTaxType = hostTaxType;
        this.hostRegistDate = hostRegistDate;
        this.hostModifyDate = hostModifyDate;
        this.hostDeleteDate = hostDeleteDate;
    }
}
