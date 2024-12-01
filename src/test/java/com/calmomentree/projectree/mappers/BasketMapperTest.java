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
    @DisplayName("장바구니 추가")
    void basketAdd() {
        Basket input = new Basket();
        input.setQuantity(5);
        input.setProdId(3);
        input.setMemberId(1);

        int output = basketMapper.insert(input);

        log.debug("장바구니 추가: " + output);
    }


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

    @Test
    @DisplayName("장바구니 수량 변경테스트")
    void orderBasketEditTest() {
        Basket input = new Basket();
        input.setBasketId(3);
        input.setQuantity(7);

        int output = basketMapper.update(input);

        log.debug("장바구니 수량 변경 테스트 : " + output);
        log.debug(input.toString());

    }

    @Test
    @DisplayName("장바구니 카운트")
    void basketCount() {
        Basket input = new Basket();
        input.setMemberId(1);

        int output = basketMapper.selectCount(input);

        log.debug("장바구니 카운트: " + output);
    }

    @Test
    @DisplayName("장바구니 삭제 테스트")
    void basketDelete() {
        Basket input = new Basket();
        input.setBasketId(1);
        input.setMemberId(1);

        int output = basketMapper.delete(input);

        log.debug("장바구니 삭제: " + output);
    }

    @Test
    @DisplayName("장바구니 중복확인")
    void basketCheck() {
        Basket input = new Basket();
        input.setProdId(3);
        input.setMemberId(1);
        
        int output = basketMapper.basketCheck(input);

        log.debug("장바구니 중복확인: " + output);
    }
}
