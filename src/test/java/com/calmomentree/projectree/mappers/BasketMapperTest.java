package com.calmomentree.projectree.mappers;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.calmomentree.projectree.models.Basket;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class BasketMapperTest {
    
    @Autowired
    private BasketMapper basketMapper;

    @Test
    @DisplayName("장바구니 조회 테스트")
    void orderBasketListTest() {
        Basket input = new Basket();
        input.setMemberId(1);

        List<Basket> output = basketMapper.selectList(input);

        for(Basket item : output) {
            log.debug("장바구니 조회: " + item.toString());
        }

    }
}
