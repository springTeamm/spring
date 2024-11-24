package com.spring.demo.security.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "Host_Info")
@Getter
@Setter
public class HostInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Host_Info_num")
    private Long id;

    @OneToOne
    @JoinColumn(name = "Host_num")
    private HostEntity host;

    @Column(name = "Host_Bis_Address")
    private String businessAddress;

    @Column(name = "Host_Bis_Item")
    private String businessItem;

    @Column(name = "Host_Regist_Num")
    private String registrationNumber;

    @Column(name = "Host_Bis_Type")
    private String businessType;

    @Column(name = "Host_Company_Name")
    private String companyName;

    @Column(name = "Host_Corp_Name")
    private String corporateName;

    @Column(name = "Host_Corepsocial_Num")
    private String corporateNumber;

    @Column(name = "Host_open_Date")
    private LocalDateTime openDate;

    @Column(name = "Host_Tax_Type")
    private String taxType;

    @Column(name = "Host_regist_date")
    private LocalDateTime registDate;

    @Column(name = "Host_modify_date")
    private LocalDateTime modifyDate;

    @Column(name = "Host_delete_date")
    private LocalDateTime deleteDate;
}
