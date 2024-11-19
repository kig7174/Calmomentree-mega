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
        input.setBoardTitle("게시글 제목임.");
        input.setBoardContent("문의사항 없는데...");
        input.setIsPublic("N");
        input.setBoardPw("1234");
        input.setUploadImg(null);
        input.setMemberId(1);

        int output = boardMapper.insert(input);

        log.debug("output: " + output);
        log.debug("board: " + input.toString());
    }

    @Test
    @DisplayName("게시글 조회 테스트")
    void boardSelectTest() {
        Board input = new Board();
        input.setBoardId(3);

        Board output = boardMapper.selectItem(input);
        log.debug("output: " + output.toString());
    }

    
    @Test
    @DisplayName("게시판 목록 조회 테스트")
    void boardCategoryTest() {
        Board input = new Board();
        input.setBoardCategory("qna");
        List<Board> output = boardMapper.categoryList(input);

        for(Board item : output) {
            log.debug("output: " + item.toString());
        }
    }
}
