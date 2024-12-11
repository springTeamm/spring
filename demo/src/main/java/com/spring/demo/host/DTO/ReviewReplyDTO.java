package com.spring.demo.host.DTO;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ReviewReplyDTO {
    private Integer reviewId;
    private String reviewContent;
    private String userName; // 작성자 이름




}
