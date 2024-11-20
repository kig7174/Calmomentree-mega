package com.calmomentree.projectree.mappers;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.calmomentree.projectree.models.Board;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class BoardMepperTest {
    
    @Autowired
    private BoardMapper boardMapper;

    @Test
    @DisplayName("게시글 등록 테스트")
    void boardUploadTest() {
        Board input = new Board();
        input.setBoardCategory("qna");
        input.setBoardTitle("리뷰게시글 제목임.");
        input.setBoardContent("리뷰입니다...");
        input.setIsPublic("N");
        input.setBoardPw("1234");
        input.setUploadImg(null);
        input.setMemberId(2);

        int output = boardMapper.insert(input);
       
        log.debug("output: " + output);
        log.debug("board: " + input.toString());
       
    }

    @Test
    @DisplayName("게시글 조회 테스트")
    void boardSelectTest() {
        Board input = new Board();
        input.setBoardId(5);

        Board output = boardMapper.selectItem(input);
        
        log.debug("output: " + output.toString());
       
    }

    
    @Test
    @DisplayName("게시판 카테고리 목록 조회 테스트")
    void boardCategoryTest() {
        Board input = new Board();
        input.setBoardCategory("review");
        List<Board> output = boardMapper.boardList(input);

        for(Board item : output) {
            
            log.debug("카테고리: " + input.getBoardCategory());
            log.debug("카테고리 목록조회: " + item.toString());

         }
    }

    @Test
    @DisplayName("게시판 작성자 목록 조회 테스트")
    void boardWriterTest() {
        Board input = new Board();
        input.setMemberId(1);
        List<Board> output = boardMapper.boardList(input);

        for(Board item : output) {
            log.debug("작성자: " + input.getMemberId());
            log.debug("작성자 목록조회: " + item.toString());
            
        }
    }

    @Test
    @DisplayName("게시판 검색 테스트")
    void boardSearchTest() {
        Board input = new Board();
        input.setBoardTitle("리뷰");
        List<Board> output = boardMapper.boardList(input);

        for(Board item : output) {
            log.debug("검색어: " + input.getBoardTitle());
            log.debug("검색 목록조회: " + item.toString());
            
        }
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void boardEditTest() {
        Board input = new Board();
        input.setBoardContent("게시글내용 수정해볼까?");
        input.setIsPublic("Y");

        input.setBoardId(5);
        input.setMemberId(1);

        int output = boardMapper.update(input);
        
        log.debug("게시글번호: " + input.getBoardId());
        log.debug("output: " + output);
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void boardDeleteTest() {
        Board input = new Board();
        input.setBoardId(4);

        int output = boardMapper.delete(input);

        log.debug("output: " + output);
    }
}
