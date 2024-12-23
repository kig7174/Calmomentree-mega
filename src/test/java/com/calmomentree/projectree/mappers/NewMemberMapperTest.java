package com.calmomentree.projectree.mappers;

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
       int output = newMemberMapper.insert();
        
       log.debug("신규회원 추가: " + output);
    }

    @Test
    @DisplayName("신규 회원 조회 테스트")
    void newMemberSelect() {
        NewMember input = new NewMember();
        input.setId(1);

        NewMember output = newMemberMapper.selectItem(input);

        log.debug("신규 회원 조회: " + output.toString());
    }

    @Test
    @DisplayName("주간 신규 회원 조회 테스트")
    void newMemberWeekly() {
        newMemberMapper.selectWeekly().forEach(item -> {
            log.debug("주간 신규 회원 조회: " + item);
        });
    }
}
