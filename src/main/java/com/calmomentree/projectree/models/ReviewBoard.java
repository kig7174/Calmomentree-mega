package com.calmomentree.projectree.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 리뷰 게시글 정보 테이블 구조를 정의하는 클래스
 */
@Data
public class ReviewBoard {
    private int reviewBoardId;      // 리뷰게시글 번호
    private String reviewTitle;     // 리뷰게시글 제목
    private String reviewContent;   // 리뷰게시글 내용
    private int rating;             // 평점
    private String writeDate;       // 작성일
    private String editDate;        // 수정일

    private int prodId;             // 상품번호
    private int memberId;           // 회원번호

    private int rownum;             // 게시글 카운트

    @Getter
    @Setter
    private static int listCount = 0;

    @Getter
    @Setter
    private static int offset = 0;
}
