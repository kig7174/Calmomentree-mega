package com.calmomentree.projectree.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class SaleServiceTest {
    @Autowired
    private SaleService saleService;

    @Test
    @DisplayName("매출 집계 테스트")
    public void saleInsertTest() {
        try {
            saleService.addItem();
        } catch (Exception e) {
            log.error("매출 집계 실패", e);
            return;
        }
    }
}
