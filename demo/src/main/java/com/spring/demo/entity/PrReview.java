package com.spring.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "pr_review")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PrReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Review_num") //리뷰번호
    private Integer reviewNum;

    @Column(name = "Booking_num") //예약번호
    private Integer bookingNum;

    @Column(name = "User_num") //댓글작성 유저번호
    private Integer userNum;

    @Column(name = "Review_date") //댓글 작성 날짜
    private Date reviewDate;

    @Column(name = "Review_text") //댓글 내용
    private String reviewText;

    @Column(name = "Review_star_score") //댓글 별점수
    private Integer reviewStarScore;

    @Column(name = "Top_review_num") //상위 댓글 번호
    private Integer topReviewNum;

    public PrReview(Integer reviewNum, Integer bookingNum, Integer userNum, Date reviewDate, String reviewText, Integer reviewStarScore, Integer topReviewNum) {
        this.reviewNum = reviewNum;
        this.bookingNum = bookingNum;
        this.userNum = userNum;
        this.reviewDate = reviewDate;
        this.reviewText = reviewText;
        this.reviewStarScore = reviewStarScore;
        this.topReviewNum = topReviewNum;
    }
}
