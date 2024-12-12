package com.spring.demo.host.DTO;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HostReplyDTO {
    private Integer reviewNum; // 답글이 달릴 리뷰 번호
    private String replyText;  // 답글 내용
}
