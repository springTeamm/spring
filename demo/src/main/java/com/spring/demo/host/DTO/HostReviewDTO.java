package com.spring.demo.host.DTO;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class HostReviewDTO {
    private Integer reviewNum;    // 리뷰 번호
    private String hostAddress;   // 사업장 주소
    private String roomName;      // 연습실 이름
    private String userName;      // 유저 이름
    private Integer reviewStarScore; // 별점

    public HostReviewDTO(Integer reviewNum,String hostAddress, String roomName, String userName, Integer reviewStarScore) {
        this.reviewNum = reviewNum;
        this.hostAddress = hostAddress;
        this.roomName = roomName;
        this.userName = userName;
        this.reviewStarScore = reviewStarScore;
    }
}
