package com.spring.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "community_reply")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CommunityReply {
    @Id
    @GeneratedValue
    @Column(name = "C_reply_num") //커뮤니티 댓글 번호
    private Integer cReplyNum;

    @Column(name = "C_num") //커뮤니티 번호
    private Integer cNum;

    @Column(name = "User_num") //댓글작성 유저 번호
    private Integer userNum;

    @Column(name = "C_reply_text")//커뮤 댓글 내용
    private String cReplyText;

    @Column(name = "C_reply_date") //커뮤 댓글 생성 날짜
    private Date cReplyDate;

    @Column(name = "Top_reply_num") //커뮤 상위 댓글
    private Integer topReplyNum;

    public CommunityReply(Integer cReplyNum, Integer cNum, Integer userNum, String cReplyText, Date cReplyDate, Integer topReplyNum) {
        this.cReplyNum = cReplyNum;
        this.cNum = cNum;
        this.userNum = userNum;
        this.cReplyText = cReplyText;
        this.cReplyDate = cReplyDate;
        this.topReplyNum = topReplyNum;
    }
}
