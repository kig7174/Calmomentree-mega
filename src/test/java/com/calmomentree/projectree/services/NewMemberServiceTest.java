package com.calmomentree.projectree.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class NewMemberServiceTest {
    @Autowired
    private NewMemberService newMemberService;

    @Test
    @DisplayName("신규회원 추가 테스트")
    void newMemberAdd() throws Exception {

        try {
            newMemberService.addItem();

        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }
    }

    @Test
    @DisplayName("신규 회원 조회 테스트")
    void newMemberSelect() throws Exception {
       
        try {
            newMemberService.getItem();

        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }
    }
    
    @Test
    @DisplayName("주간 신규 회원 조회 테스트")
    void newMemberWeekly() throws Exception {
        try {
            newMemberService.getWeeklyList();

        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }
    }

    @Test
    @DisplayName("신규 회원 자동 삭제 테스트")
    void newMemberAutoDelete() throws Exception {
        try {
            newMemberService.autoDelete();

        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }
    }
}
