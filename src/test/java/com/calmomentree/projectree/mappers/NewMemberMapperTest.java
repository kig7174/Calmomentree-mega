package com.calmomentree.projectree.mappers;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.calmomentree.projectree.models.NewMember;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class NewMemberMapperTest {
    @Autowired
    private NewMemberMapper newMemberMapper;

    @Test
    @DisplayName("신규회원 추가 테스트")
    void newMemberAdd() {

        NewMember input = new NewMember();
       int output = newMemberMapper.insert(input);
        
       if(output == 0) {
        newMemberMapper.insertDefault(input);
        log.debug("신규회원 없음 - 기본값 0 삽입 완료");
       }

       log.debug("신규회원 추가: " + output);
    }

    @Test
    @DisplayName("신규회원 기본값 추가 테스트")
    void newMemberAddDefault() {
        NewMember input = new NewMember();
        int output = newMemberMapper.insertDefault(input);

        log.debug("신규회원 기본값 추가: " + output);
    }

    @Test
    @DisplayName("신규 회원 주간 조회 테스트")
    void newMemberWeekly() {
       List<NewMember> output = newMemberMapper.selectWeekly();

        log.debug("주간 조회: " + output.toString());
    }

    @Test
    @DisplayName("신규 회원 월간 조회 테스트")
    void newMemberMonthly() {
        newMemberMapper.selectMonthly().forEach(item -> {
            log.debug("월간 조회: " + item.toString());
        });
    }

    // @Test
    // @DisplayName("신규 회원 자동 삭제 테스트")
    // void newMemberAutoDelete() {
    //     int output = newMemberMapper.autoDelete();

    //     log.debug("신규 회원 자동 삭제: " + output);
    // }
}
