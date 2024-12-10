package com.calmomentree.projectree.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calmomentree.projectree.mappers.OrderMapper;
import com.calmomentree.projectree.models.Order;
import com.calmomentree.projectree.services.OrderService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order addItem(Order input) throws Exception {
        int rows = 0;

        try {
            rows = orderMapper.insert(input);

            if (rows == 0) {
                throw new Exception("저장된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 저장에 실패했습니다.", e);
            throw e;
        }

        return orderMapper.selectItem(input);
    }

    @Override
    public Order editItem(Order input) throws Exception {
        int rows = 0;

        try {
            rows = orderMapper.update(input);

            if (rows == 0) {
                throw new Exception("수정된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 수정에 실패했습니다.", e);
            throw e;
        }

        return orderMapper.selectItem(input);
    }

    @Override
    public int deleteItem(Order input) throws Exception {
        int rows = 0;

        try {
            rows = orderMapper.delete(input);

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
    public Order getItem(Order input) throws Exception {
        Order output = null;

        try {
            output = orderMapper.selectItem(input);

            if (output == null) {
                throw new Exception("조회된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public List<Order> getList(Order input) throws Exception {
        List<Order> output = null;

        try {
            output = orderMapper.selectList(input);
        } catch (Exception e) {
            log.error("목록 조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public int getCount(Order input) throws Exception {
        int output = 0;

        try {
            output = orderMapper.selectCount(input);
        } catch (Exception e) {
            log.error("데이터 집계에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    /**
     * 주문서 중복 확인용
     */
    @Override
    public int overCount(Order input) throws Exception {
        int output = 0;

        try {
            output = orderMapper.overCount(input);

        } catch (Exception e) {
            log.error("중복여부 확인에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public int deleteByCancelOrder() throws Exception {
        int rows = 0;

        try {
            rows = orderMapper.deleteByCancelOrder();
        } catch (Exception e) {
            log.error("삭제 실패", e);
            throw e;
        }

        return rows;
    }
}