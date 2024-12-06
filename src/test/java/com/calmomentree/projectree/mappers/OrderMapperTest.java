package com.calmomentree.projectree.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.calmomentree.projectree.models.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class OrderMapperTest {
    
    @Autowired
    private OrderMapper orderMapper;

    @Test
    @DisplayName("주문 페이지 테스트")
    void orderAdd() {
        Order order = new Order();
        order.setMemberName("주문자이름");
        order.setMemberEmail("ddfda@naver.com");
        order.setMemberPostcode(12345);
        order.setMemberAddr1("주소1");
        order.setMemberAddr2("상세주소");
        order.setMemberTel("01011113333");
        order.setReceiverName("받는사람이름");
        order.setReceiverPostcode(67878);
        order.setReceiverAddr1("주소2");
        order.setReceiverAddr2("상세주소2");
        order.setReceiverTel("01022223333");
        order.setTotalPrice(3213123);
        order.setReq(".....");
        order.setMemberId(1);

        int output = orderMapper.insert(order);

        log.debug("output: " + output);
    }
}
