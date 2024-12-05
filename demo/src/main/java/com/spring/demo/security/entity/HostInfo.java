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

    @Column(name = "Business_address", nullable = false)
    private String businessAddress; // 사업장 주소

    @Column(name = "Business_type", nullable = false)
    private String businessType; // 업태

    @Column(name = "Business_classification", nullable = false)
    private String businessClassification; // 업종

    @Column(name = "Business_category", nullable = false)
    private String businessCategory; // 사업자 구분

    @Column(name = "Company_name", nullable = false)
    private String companyName; // 상호명

    @Column(name = "Host_regist_num")
    private String hostRegistNum;  // 사업자 등록 번호

    @Column(name = "Online_sales_report_number", nullable = false)
    private String onlineSalesReportNumber; // 통신판매업 신고 번호

    @Column(name = "Representative_name", nullable = false)
    private String representativeName; // 대표자 이름

    @Column(name = "Bank_name", nullable = false)
    private String bankName; // 은행명

    @Column(name = "Settlement_account", nullable = false)
    private String settlementAccount; // 정산 대금 입금 계좌

    @Column(name = "Practice_room_address", nullable = false)
    private String practiceRoomAddress; // 연습실 주소

    @Column(name = "Practice_room_phone_number", nullable = false)
    private String practiceRoomPhoneNumber; // 연습실 전화번호


    @Column(name = "Host_regist_date")
    private LocalDateTime hostRegistDate; // 호스트 정보 등록일

    @Column(name = "Host_modify_date")
    private LocalDateTime hostModifyDate; // 정보 수정일

    @Column(name = "Host_delete_date")
    private LocalDateTime hostDeleteDate; // 삭제일 (삭제 예정일자)

    public HostInfo(Hosts host,
                    String businessAddress,
                    String businessType,
                    String businessClassification,
                    String businessCategory,
                    String companyName,
                    String hostRegistNum,
                    String onlineSalesReportNumber,
                    String representativeName,
                    String bankName,
                    String settlementAccount,
                    String practiceRoomAddress,
                    String practiceRoomPhoneNumber) {
        this.host = host;
        this.businessAddress = businessAddress;
        this.businessType = businessType;
        this.businessClassification = businessClassification;
        this.businessCategory = businessCategory;
        this.companyName = companyName;
        this.hostRegistNum = hostRegistNum;
        this.onlineSalesReportNumber = onlineSalesReportNumber;
        this.representativeName = representativeName;
        this.bankName = bankName;
        this.settlementAccount = settlementAccount;
        this.practiceRoomAddress = practiceRoomAddress;
        this.practiceRoomPhoneNumber = practiceRoomPhoneNumber;
        this.hostRegistDate = LocalDateTime.now(); // 등록일 초기화
    }
}