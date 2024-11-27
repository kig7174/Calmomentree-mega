package com.calmomentree.projectree.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calmomentree.projectree.mappers.ProdImgMapper;
import com.calmomentree.projectree.models.ProdImg;
import com.calmomentree.projectree.services.ProdImgService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProdImgServiceImpl implements ProdImgService {

    @Autowired
    private ProdImgMapper prodImgMapper;

    @Override
    public ProdImg addItem(ProdImg input) throws Exception {
        int rows = 0;

        try {
            rows = prodImgMapper.insert(input);

            if (rows == 0) {
                throw new Exception("저장된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 저장에 실패했습니다.", e);
            throw e;
        }

        return prodImgMapper.selectItem(input);
    }

    @Override
    public ProdImg editItem(ProdImg input) throws Exception {
        int rows = 0;

        try {
            rows = prodImgMapper.update(input);

            if (rows == 0) {
                throw new Exception("수정된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 수정에 실패했습니다.", e);
            throw e;
        }

        return prodImgMapper.selectItem(input);
    }

    @Override
    public int deleteItem(ProdImg input) throws Exception {
        int rows = 0;

        try {
            rows = prodImgMapper.delete(input);

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
    public ProdImg getItem(ProdImg input) throws Exception {
        ProdImg output = null;

        try {
            output = prodImgMapper.selectItem(input);

            if (output == null) {
                throw new Exception("조회된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("교수 조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public List<ProdImg> getList(ProdImg input) throws Exception {
        List<ProdImg> output = null;

        try {
            output = prodImgMapper.selectList(input);
        } catch (Exception e) {
            log.error("교수 목록 조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public int getCount(ProdImg input) throws Exception {
        int output = 0;

        try {
            output = prodImgMapper.selectCount(input);
        } catch (Exception e) {
            log.error("데이터 집계에 실패했습니다.", e);
            throw e;
        }

        return output;
    }
}