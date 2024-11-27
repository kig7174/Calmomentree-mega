package com.calmomentree.projectree.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calmomentree.projectree.mappers.OrderItemMapper;
import com.calmomentree.projectree.models.OrderItem;
import com.calmomentree.projectree.services.OrderItemService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public OrderItem addItem(OrderItem input) throws Exception {
        int rows = 0;

        try {
            rows = orderItemMapper.insert(input);

            if (rows == 0) {
                throw new Exception("저장된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 저장에 실패했습니다.", e);
            throw e;
        }

        return orderItemMapper.selectItem(input);
    }

    @Override
    public OrderItem editItem(OrderItem input) throws Exception {
        int rows = 0;

        try {
            rows = orderItemMapper.update(input);

            if (rows == 0) {
                throw new Exception("수정된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 수정에 실패했습니다.", e);
            throw e;
        }

        return orderItemMapper.selectItem(input);
    }

    @Override
    public int deleteItem(OrderItem input) throws Exception {
        int rows = 0;

        try {
            rows = orderItemMapper.delete(input);

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
    public OrderItem getItem(OrderItem input) throws Exception {
        OrderItem output = null;

        try {
            output = orderItemMapper.selectItem(input);

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
    public List<OrderItem> getList(OrderItem input) throws Exception {
        List<OrderItem> output = null;

        try {
            output = orderItemMapper.selectList(input);
        } catch (Exception e) {
            log.error("목록 조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public int getCount(OrderItem input) throws Exception {
        int output = 0;

        try {
            output = orderItemMapper.selectCount(input);
        } catch (Exception e) {
            log.error("데이터 집계에 실패했습니다.", e);
            throw e;
        }

        return output;
    }
}