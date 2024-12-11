package com.calmomentree.projectree.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.calmomentree.projectree.models.Order;
import com.calmomentree.projectree.services.OrderService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableScheduling
public class OrderScheduler {
    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0 0/30 * * * *")
    public void deleteByCancelOrder() {
        int deleteOrders = 0;

        try {
            deleteOrders = orderService.deleteByCancelOrder();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return;
        }

        log.info("=============================================");
        log.info("삭제 처리된 주문 수 : " + deleteOrders + "개");
        log.info("=============================================");
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void orderCountReset() {
        Order.orderCountReset();
        log.info("============================== 주문 번호 카운트 초기화 ==============================");
    }
}
