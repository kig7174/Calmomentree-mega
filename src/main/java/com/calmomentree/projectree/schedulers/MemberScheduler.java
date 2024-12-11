package com.calmomentree.projectree.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.calmomentree.projectree.services.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableAsync
public class MemberScheduler {

    @Autowired
    private MemberService memberService;

    @Scheduled(cron = "0 0 4 * * *")
    public void deleteOutMebmers() {
        int outMembers = 0;

        try {
            outMembers = memberService.deleteOutMebmers();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return;
        }

        log.info("=============================================");
        log.info("탈퇴 처리가 완료된 회원 수 : " + outMembers + "명");
        log.info("=============================================");
    }
}
