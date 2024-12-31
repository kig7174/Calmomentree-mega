package com.calmomentree.projectree.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.calmomentree.projectree.models.NewMember;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class NewMemberServiceTest {
    @Autowired
    private NewMemberService newMemberService;

    @Test
    @DisplayName("신규회원 추가 테스트")
    void newMemberAdd() throws Exception {
        NewMember input = new NewMember();
        try {
            int test = newMemberService.addItem(input);

            if(test == 0) {
                newMemberService.addDefault(input);
                log.debug("신규회원 없음 - 기본값 0 삽입 완료");
            } else {
                log.debug("신규회원 데이터 집계 완료", test);
            }
        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }
    }

    @Test
    @DisplayName("신규 회원 주간 조회 테스트")
    void newMemberWeekly() throws Exception {
       
        try {
            newMemberService.getWeeklyList();

        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }
    }
    
    @Test
    @DisplayName("신규 회원 월간 조회 테스트")
    void newMemberMonthly() throws Exception {
        try {
            newMemberService.getMonthlyList();

        } catch (Exception e) {
            log.error("Mapper 구현 에러", e);
            throw e;
        }
    }

    // @Test
    // @DisplayName("신규 회원 자동 삭제 테스트")
    // void newMemberAutoDelete() throws Exception {
    //     try {
    //         newMemberService.autoDelete();

    //     } catch (Exception e) {
    //         log.error("Mapper 구현 에러", e);
    //         throw e;
    //     }
    // }
}
