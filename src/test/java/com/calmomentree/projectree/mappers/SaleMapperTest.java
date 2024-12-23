package com.calmomentree.projectree.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class SaleMapperTest {
    @Autowired
    private SaleMapper saleMapper;

    @Test
    @DisplayName("매출 집계 테스트")
    public void saleInsertTest() {
        saleMapper.insert();
    }
}
