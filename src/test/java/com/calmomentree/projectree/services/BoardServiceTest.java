package com.calmomentree.projectree.services;

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
        input.setBoardTitle("게시글 제목 입니다.");
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
        
        log.debug("output: "+  output.toString());
    }
}
