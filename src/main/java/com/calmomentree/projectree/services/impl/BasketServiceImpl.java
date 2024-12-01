package com.calmomentree.projectree.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calmomentree.projectree.mappers.BasketMapper;
import com.calmomentree.projectree.models.Basket;
import com.calmomentree.projectree.services.BasketService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BasketServiceImpl implements BasketService {

    @Autowired
    private BasketMapper basketMapper;

    @Override
    public Basket addItem(Basket input) throws Exception {
        int rows = 0;

        int check = 0;

        try {
            check = basketMapper.basketCheck(input);

            if(check > 0) {
                throw new Exception("이미 장바구니에 등록되어있습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 저장에 실패했습니다.", e);
            throw e;
        }
        
        try {
            rows = basketMapper.insert(input);

            if (rows == 0) {
                throw new Exception("저장된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 저장에 실패했습니다.", e);
            throw e;
        }

        return basketMapper.selectItem(input);
    }

    @Override
    public Basket editItem(Basket input) throws Exception {
        int rows = 0;

        try {
            rows = basketMapper.update(input);

            if (rows == 0) {
                throw new Exception("수정된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 수정에 실패했습니다.", e);
            throw e;
        }

        return basketMapper.selectItem(input);
    }

    @Override
    public int deleteItem(Basket input) throws Exception {
        int rows = 0;

        try {
            rows = basketMapper.delete(input);

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
    public Basket getItem(Basket input) throws Exception {
        Basket output = null;

        try {
            output = basketMapper.selectItem(input);

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
    public List<Basket> getList(Basket input) throws Exception {
        List<Basket> output = null;

        try {
            output = basketMapper.selectList(input);
        } catch (Exception e) {
            log.error("목록 조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public int getCount(Basket input) throws Exception {
        int output = 0;

        try {
            output = basketMapper.selectCount(input);
        } catch (Exception e) {
            log.error("데이터 집계에 실패했습니다.", e);
            throw e;
        }

        return output;
    }
}