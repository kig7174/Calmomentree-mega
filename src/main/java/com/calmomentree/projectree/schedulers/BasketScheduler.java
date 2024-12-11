package com.calmomentree.projectree.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.calmomentree.projectree.services.BasketService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableScheduling
public class BasketScheduler {
    
    @Autowired
    private BasketService basketService;

    @Scheduled(cron = "0 0 * * * *")
    public void deleteByOverDays() {
        int deleteBaskets = 0;

        try {
            deleteBaskets = basketService.deleteByOverDays();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return;
        }
        log.info("=============================================");
        log.info("삭제된 장바구니 수 : " + deleteBaskets + "개");
        log.info("=============================================");
    }
}
