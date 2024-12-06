package com.spring.demo.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder  // 상속을 고려
public class HostDTO extends UserDTO {

    @NotBlank(message = "호스트 유형은 필수 입력 항목입니다.")
    private String hostType;

    @NotBlank(message = "상호명은 필수 입력 항목입니다.")
    private String companyName;

    @NotBlank(message = "사업자 등록 번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^\\d{10}$", message = "사업자 등록 번호는 10자리 숫자여야 합니다.")
    private String businessRegistrationNumber;

    @NotBlank(message = "사업장 소재지는 필수 입력 항목입니다.")
    private String businessAddress;

    @NotBlank(message = "사업자 구분은 필수 입력 항목입니다.")
    private String businessCategory;

    @NotBlank(message = "업태는 필수 입력 항목입니다.")
    private String businessType;

    @NotBlank(message = "업종은 필수 입력 항목입니다.")
    private String businessClassification;

    @NotBlank(message = "통신판매업 신고 번호는 필수 입력 항목입니다.")
    private String onlineSalesReportNumber;

    @NotBlank(message = "대표자 이름은 필수 입력 항목입니다.")
    private String representativeName;

    @NotBlank(message = "은행명은 필수 입력 항목입니다.")
    private String bankName;

    @NotBlank(message = "정산대금 입금 계좌는 필수 입력 항목입니다.")
    private String settlementAccount;

    @NotBlank(message = "연습실 주소는 필수 입력 항목입니다.")
    private String practiceRoomAddress;

    @NotBlank(message = "연습실 전화번호는 필수 입력 항목입니다.")
    private String practiceRoomPhoneNumber;

    private String confirmUserPassword; // 비밀번호 확인 필드 (비즈니스 로직에서 사용)

}
