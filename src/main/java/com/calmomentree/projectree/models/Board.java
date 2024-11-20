package com.calmomentree.projectree.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 게시판 테이블 구조를 정의하는 클래스
 */
@Data
public class Board {
    private int boardId;            // 게시글 번호
    private String boardCategory;   // 게시글 카테고리
    private String boardTitle;      // 제목
    private String boardContent;    // 내용
    private String writeDate;       // 작성일
    private String editDate;        // 수정일
    private String isPublic;        // 공개여부
    private String boardPw;         // 게시글 비밀번호
    private String uploadImg;       // 첨부파일 사진 업로드 경로
    
    private int memberId;           // 회원 번호
    private String userName;        // 회원 이름

    private String rownum;          // 순번
    
    @Getter
    @Setter
    private static int listCount = 0;

    @Getter
    @Setter
    private static int offset = 0;
}
