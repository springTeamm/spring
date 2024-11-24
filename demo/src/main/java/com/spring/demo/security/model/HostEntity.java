package com.spring.demo.security.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Host_Info")
@Getter
@Setter
@NoArgsConstructor
public class HostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Host_Info_num")
    private Long id;

    @Column(name = "Host_num", nullable = false)
    private Long userId; // User 테이블의 ID와 매핑

    @Column(name = "Host_Bis_Address", nullable = false)
    private String businessAddress;

    @Column(name = "Host_Bis_Item")
    private String businessItem;

    @Column(name = "Host_Regist_Num", nullable = false)
    private String businessRegistrationNumber;

    @Column(name = "Host_Bis_Type", nullable = false)
    private String businessType;

    @Column(name = "Host_Company_Name", nullable = false)
    private String companyName;

    @Column(name = "Host_Corp_Name")
    private String corporateName;

    @Column(name = "Host_Corepsocial_Num")
    private String communicationSalesNumber;

    @Column(name = "Host_open_Date")
    private String openDate;

    @Column(name = "Host_Tax_Type")
    private String taxType;

    @Column(name = "Host_regist_date")
    private String registrationDate;

    @Column(name = "Host_modify_date")
    private String modifyDate;

    @Column(name = "Host_delete_date")
    private String deleteDate;
}