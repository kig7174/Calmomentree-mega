package com.calmomentree.projectree.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calmomentree.projectree.mappers.SaleMapper;
import com.calmomentree.projectree.models.Sale;
import com.calmomentree.projectree.services.SaleService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleMapper saleMapper;

    @Override
    public void addItem() throws Exception {
        int rows = 0;

        try {
            rows = saleMapper.insert();

            if (rows == 0) {
                saleMapper.insertDefault();
            }
        } catch (Exception e) {
            log.error("데이터 저장에 실패했습니다.", e);
            throw e;
        }
    }

    @Override
    public Sale editItem(Sale input) throws Exception {
        int rows = 0;

        try {
            rows = saleMapper.update(input);

            if (rows == 0) {
                throw new Exception("수정된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 수정에 실패했습니다.", e);
            throw e;
        }

        return saleMapper.selectItem(input);
    }

    @Override
    public int deleteItem(Sale input) throws Exception {
        int rows = 0;

        try {
            rows = saleMapper.delete(input);

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
    public Sale getItem(Sale input) throws Exception {
        Sale output = null;

        try {
            output = saleMapper.selectItem(input);

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
    public List<Sale> getList(Sale input) throws Exception {
        List<Sale> output = null;

        try {
            output = saleMapper.selectList(input);
        } catch (Exception e) {
            log.error("교수 목록 조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public int getCount(Sale input) throws Exception {
        int output = 0;

        try {
            output = saleMapper.selectCount(input);
        } catch (Exception e) {
            log.error("데이터 집계에 실패했습니다.", e);
            throw e;
        }

        return output;
    }
}
