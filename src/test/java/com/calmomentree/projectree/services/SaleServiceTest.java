package com.calmomentree.projectree.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.calmomentree.projectree.models.Sale;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class SaleServiceTest {
    @Autowired
    private SaleService saleService;

    @Test
    @DisplayName("매출 집계 테스트")
    public void saleInsertTest() {
        Sale input = new Sale();
        
        try {
            saleService.addItem(input);
        } catch (Exception e) {
            log.error("매출 집계 실패", e);
            return;
        }
    }

    @Test
    @DisplayName("월 매출 확인 테스트")
    public void saleListTest() {
        try {
            saleService.getList(null);
        } catch (Exception e) {
            log.error("매출 확인 실패", e);
            return;
        }
    }
}
