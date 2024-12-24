package com.calmomentree.projectree.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.calmomentree.projectree.services.NewMemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableAsync
public class NewMemberScheduler {
    @Autowired
    private NewMemberService newMemberService;

    @Scheduled(cron = "0 0 1 * * *")
    public void newMemberInsert() {

        try {
            int be = newMemberService.addItem();

            if (be == 0) {
                newMemberService.addDefault();
                log.debug("신규회원 없음 - 기본값 0 삽입 완료");
            } else {
                log.debug("신규회원 데이터 집계 완료", be);
            }
        } catch (Exception e) {
            log.error("신규회원 데이터 집계 실패", e);
            return;
        }
    }
}
