package com.calmomentree.projectree.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.calmomentree.projectree.models.Sale;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class SaleMapperTest {
    @Autowired
    private SaleMapper saleMapper;

    @Test
    @DisplayName("매출 집계 테스트")
    public void saleInsertTest() {
        Sale input = new Sale();
        saleMapper.insert(input);
    }

    @Test
    @DisplayName("매출 없을 때 테스트")
    public void saleDefaultInsert() {
        Sale input = new Sale();
        saleMapper.insertDefault(input);
    }

    @Test
    @DisplayName("월 매출 조회")
    public void saleList() {

        saleMapper.selectList(null);
    }
}
