package com.calmomentree.projectree.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.calmomentree.projectree.models.Basket;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class BasketServiceTest {
    
    @Autowired
    private BasketService basketService;

    @Test
    @DisplayName("장바구니 수량 변경 테스트")
    void basketEditTest() throws Exception {
        Basket input = new Basket();
        input.setBasketId(3);
        input.setQuantity(3);

        Basket output = null;
        try {
           output = basketService.editItem(input);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }

        if(output != null) {
            log.debug("[테스트]장바구니 수량변경: " + output);
        }
    }

    @Test
    @DisplayName("장바구니 조회 테스트")
    void basketSelectTest() throws Exception {
        Basket input = new Basket();
        input.setBasketId(3);

        Basket output = null;
        try {
           output = basketService.getItem(input);
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }

        if(output != null) {
            log.debug("[테스트]장바구니 조회: " + output);
        }
    }
}
