package com.calmomentree.projectree.mappers;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.calmomentree.projectree.models.ReviewBoard;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ReviewBoardMapperTest {
    
    @Autowired
    private ReviewBoardMapper reviewBoardMapper;

    @Test
    @DisplayName("리뷰 게시글 등록 테스트")
    void reviewAdd () {
        ReviewBoard input = new ReviewBoard();
        input.setReviewTitle("제목입니다.");
        input.setReviewContent("내용입력입니다.");
        input.setRating(5);
        input.setProdId(5);
        input.setMemberId(2);

        int output = reviewBoardMapper.insert(input);
       
        log.debug("output: " + output);
        log.debug("board: " + input.toString());
    }


    @Test
    @DisplayName("리뷰 게시글 조회 테스트")
    void reviewBoardTest() {
        List<ReviewBoard> output = reviewBoardMapper.selectList(null);
    
        for(ReviewBoard item : output) {
            log.debug("리뷰게시글: " + item.toString());
        }
       
    }
}
