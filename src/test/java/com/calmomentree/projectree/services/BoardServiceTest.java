package com.calmomentree.projectree.services;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.calmomentree.projectree.models.Board;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    @DisplayName("게시글 등록 테스트")
    void addBoardTest() throws Exception {
        Board input = new Board();
        input.setBoardCategory("qna");
        input.setBoardTitle("환불요청.");
        input.setBoardContent("게시글 내용 입니다.");
        input.setIsPublic("N");
        input.setBoardPw("1234");
        input.setUploadImg(null);

        input.setMemberId(1);

        Board output = null;

        try {
            output = boardService.addItem(input);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }

        if (output != null) {
            log.debug("output: " + output.toString());
        }

    }

    @Test
    @DisplayName("게시글 조회 테스트")
    void boardSelectTest() throws Exception {
        Board input = new Board();
        input.setBoardId(3);

        Board output = null;

        try {
            output = boardService.getItem(input);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }
        if (output != null) {
            log.debug("output: " + output.toString());
        }

    }

    @Test
    @DisplayName("게시판 카테고리 조회 테스트")
    void boardCategoryTest() throws Exception {
        Board input = new Board();
        input.setBoardCategory("qna");

        List<Board> output = null;

        try {
            output = boardService.getList(input);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }
        if (output != null) {
            for (Board item : output) {
                log.debug("카테고리: " + input.getBoardCategory());
                log.debug("카테고리 목록조회: " + item.toString());
            }
        }

    }

    @Test
    @DisplayName("게시판 작성자 목록 조회 테스트")
    void boardWriterTest() throws Exception {
        Board input = new Board();
        input.setMemberId(1);

        List<Board> output = null;

        try {
            output = boardService.getList(input);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }

        if (output != null) {
            for (Board item : output) {
                log.debug("작성자: " + input.getMemberId());
                log.debug("작성자 목록조회: " + item.toString());
            }
        }

    }

    @Test
    @DisplayName("게시판 검색 조회 테스트")
    void boardSearchTest() throws Exception {
        Board input = new Board();
        input.setBoardTitle("환불");

        List<Board> output = null;

        try {
            output = boardService.getList(input);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }

        if (output != null) {
            for (Board item : output) {
                log.debug("검색어: " + input.getBoardTitle());
                log.debug("검색 조회: " + item.toString());
            }
        }

    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void boardEditTest() throws Exception {
        Board input = new Board();
        input.setBoardContent("게시글내용 수정해볼까?");
        input.setIsPublic("N");

        input.setBoardId(5);
        input.setMemberId(1);

        Board output = null;

        try {
            output = boardService.editItem(input);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }
        if (output != null) {
            log.debug("output" + output.toString());
        }

    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void boardDeleteTest() throws Exception {
        Board input = new Board();
        input.setBoardId(3);

        try {
            boardService.deleteItem(input);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }
    }
}
