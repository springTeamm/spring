package com.spring.demo.security.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HostSignupDTO extends UserDTO {

    @NotBlank(message = "호스트 유형은 필수 입력 항목입니다.")
    private String hostType;

    @NotBlank(message = "사업자 등록 번호는 필수 입력 항목입니다.")
//    @Pattern(regexp = "^\\d{10}$", message = "사업자 등록 번호는 10자리 숫자여야 합니다.")
    private String businessRegistrationNumber;

    @NotBlank(message = "사업장 주소는 필수 입력 항목입니다.")
    private String businessAddress;

    @NotBlank(message = "사업자명은 필수 입력 항목입니다.")
    private String businessName;

    @NotBlank(message = "대표자명은 필수 입력 항목입니다.")
    private String representativeName;

    @NotBlank(message = "객실 유형은 필수 입력 항목입니다.")
    private String roomType;

    @NotBlank(message = "객실 이름 필수 입력 항목입니다.")
    private String roomName;
}
