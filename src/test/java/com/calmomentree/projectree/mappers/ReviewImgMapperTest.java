package com.calmomentree.projectree.mappers;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.calmomentree.projectree.models.ReviewImg;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ReviewImgMapperTest {
    
    @Autowired
    private ReviewImgMapper reviewImgMapper;
    
    @Test
    @DisplayName("리뷰이미지 업로드 테스트")
    void reviewImgAddTest() {
        ReviewImg input = new ReviewImg();
        input.setImgUrl(null);

        int output = reviewImgMapper.insert(input);

        log.debug("output: " + output);
        log.debug("board: " + input.toString());
    }

    @Test
    @DisplayName("리뷰이미지 조회 테스트")
    void reviewImgSelectItemTest() {
        ReviewImg input = new ReviewImg();
        input.setBoardImgId(1);

        ReviewImg output = reviewImgMapper.selectItem(input);

        log.debug("output: " + output.toString());
    }

    @Test
    @DisplayName("리뷰이미지 목록 조회 테스트")
    void reviewImgListTest() {
        ReviewImg input = new ReviewImg();
        input.setBoardImgId(1);

        List<ReviewImg> output = reviewImgMapper.selectList(input);

        for(ReviewImg item : output) {
            log.debug("리뷰이미지 LIST: " + item.toString());
        }
    }
}
