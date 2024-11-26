package com.calmomentree.projectree.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calmomentree.projectree.mappers.ReviewImgMapper;
import com.calmomentree.projectree.models.ReviewImg;
import com.calmomentree.projectree.services.ReviewImgService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReviewImgServiceImpl implements ReviewImgService {

    @Autowired
    private ReviewImgMapper reviewImgMapper;

    @Override
    public ReviewImg addItem(ReviewImg input) throws Exception {
        int rows = 0;

        try {
            rows = reviewImgMapper.insert(input);

            if (rows == 0) {
                throw new Exception("저장된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 저장에 실패했습니다.", e);
            throw e;
        }

        return reviewImgMapper.selectItem(input);
    }

    @Override
    public ReviewImg editItem(ReviewImg input) throws Exception {
        int rows = 0;

        try {
            rows = reviewImgMapper.update(input);

            if (rows == 0) {
                throw new Exception("수정된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 수정에 실패했습니다.", e);
            throw e;
        }

        return reviewImgMapper.selectItem(input);
    }

    @Override
    public int deleteItem(ReviewImg input) throws Exception {
        int rows = 0;

        try {
            rows = reviewImgMapper.delete(input);

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
    public ReviewImg getItem(ReviewImg input) throws Exception {
        ReviewImg output = null;

        try {
            output = reviewImgMapper.selectItem(input);

            if (output == null) {
                throw new Exception("조회된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public List<ReviewImg> getList(ReviewImg input) throws Exception {
        List<ReviewImg> output = null;

        try {
            output = reviewImgMapper.selectList(input);
        } catch (Exception e) {
            log.error("목록 조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public int getCount(ReviewImg input) throws Exception {
        int output = 0;

        try {
            output = reviewImgMapper.selectCount(input);
        } catch (Exception e) {
            log.error("데이터 집계에 실패했습니다.", e);
            throw e;
        }

        return output;
    }
}