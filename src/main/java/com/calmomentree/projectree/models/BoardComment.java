package com.calmomentree.projectree.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 댓글 테이블 구조를 정의하는 클래스
 */
@Data
public class BoardComment {
    private int boardCommentId; // 댓글 번호
    private String cmtContent;  // 댓글 내용
    private String writeDate;   // 작성일
    
    private int boardId;        // 게시글 번호
    private int memberId;       // 회원번호
    
    @Getter
    @Setter
    private static int listCount = 0;

    @Getter
    @Setter
    private static int offset = 0;
}