package com.calmomentree.projectree.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calmomentree.projectree.mappers.ReviewBoardMapper;
import com.calmomentree.projectree.models.ReviewBoard;
import com.calmomentree.projectree.services.ReviewBoardService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReviewBoardServiceImpl implements ReviewBoardService {

    @Autowired
    private ReviewBoardMapper reviewBoardMapper;

    @Override
    public ReviewBoard addItem(ReviewBoard input) throws Exception {
        int rows = 0;

        try {
            rows = reviewBoardMapper.insert(input);

            if (rows == 0) {
                throw new Exception("저장된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 저장에 실패했습니다.", e);
            throw e;
        }

        return reviewBoardMapper.selectItem(input);
    }

    @Override
    public ReviewBoard editItem(ReviewBoard input) throws Exception {
        int rows = 0;

        try {
            rows = reviewBoardMapper.update(input);

            if (rows == 0) {
                throw new Exception("수정된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 수정에 실패했습니다.", e);
            throw e;
        }

        return reviewBoardMapper.selectItem(input);
    }

    @Override
    public int deleteItem(ReviewBoard input) throws Exception {
        int rows = 0;

        try {
            rows = reviewBoardMapper.delete(input);

            if (rows == 0) {
                throw new Exception("삭제된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 삭제에 실패했습니다.", e);
            throw e;
        }

        return rows;
    }

    @Override
    public ReviewBoard getItem(ReviewBoard input) throws Exception {
        ReviewBoard output = null;

        try {
            output = reviewBoardMapper.selectItem(input);

            if (output == null) {
                throw new Exception("조회된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("게시글 조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public List<ReviewBoard> getList(ReviewBoard input) throws Exception {
        List<ReviewBoard> output = null;

        try {
            output = reviewBoardMapper.selectList(input);
        } catch (Exception e) {
            log.error("게시글 목록 조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public int getCount(ReviewBoard input) throws Exception {
        int output = 0;

        try {
            output = reviewBoardMapper.selectCount(input);
        } catch (Exception e) {
            log.error("데이터 집계에 실패했습니다.", e);
            throw e;
        }

        return output;
    }
}
