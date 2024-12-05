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
    private Integer topReviewNum;
    private String reviewContent;

    public HostReviewDTO(Integer reviewNum,String hostAddress, String roomName, String userName, Integer reviewStarScore, Integer topReviewNum, String reviewContent) {
        this.reviewNum = reviewNum;
        this.hostAddress = hostAddress;
        this.roomName = roomName;
        this.userName = userName;
        this.reviewStarScore = reviewStarScore;
        this.topReviewNum = topReviewNum;
        this.reviewContent = reviewContent;
    }
}
