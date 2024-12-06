package com.calmomentree.projectree.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.calmomentree.projectree.models.OrderItem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class OrderItemMapperTest {
    
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Test
    @DisplayName("주문 상품 등록 테스트")
    void addOrderItem() {
        OrderItem input = new OrderItem();
        input.setProdName("상품이름");
        input.setOrderPrice(13000);
        input.setOrderQuantity(3);
        input.setProdId(2);
        input.setOrderId(2);

        int output = orderItemMapper.insert(input);

        log.debug("output: " + output);
    }
}
