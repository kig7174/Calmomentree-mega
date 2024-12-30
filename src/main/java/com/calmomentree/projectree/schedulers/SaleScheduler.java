package com.calmomentree.projectree.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.calmomentree.projectree.models.Sale;
import com.calmomentree.projectree.services.SaleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableScheduling
public class SaleScheduler {
    @Autowired
    private SaleService saleService;

    @Scheduled(cron = "0 0 1 * * *")
    public void saleInsert() {
        Sale input = new Sale();

        try {
            saleService.addItem(input);
        } catch (Exception e) {
            log.error("데이터 집계 실패", e);
            return;
        }
    }
}
