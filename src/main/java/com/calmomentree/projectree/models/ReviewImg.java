package com.calmomentree.projectree.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 게시글 첨부 이미지 정보 테이블 구조를 정의하는 클래스
 */
@Data
public class ReviewImg {
    private int boardImgId;     // 게시글 이미지 번호
    private String imgUrl;      // 게시글 이미지 경로

    private int reviewBoardId;  // 리뷰 게시글 번호
    

    @Getter
    @Setter
    private static int listCount = 0;

    @Getter
    @Setter
    private static int offset = 0;
}