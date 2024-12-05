package com.spring.demo.host.DTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

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
