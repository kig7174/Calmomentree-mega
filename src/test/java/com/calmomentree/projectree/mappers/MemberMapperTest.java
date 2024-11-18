package com.calmomentree.projectree.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.calmomentree.projectree.models.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    @Test
    @DisplayName("회원가입 테스트")
    void memberJoinTest() {
        Member input = new Member();
        input.setUserName("김인겸");
        input.setUserId("test1");
        input.setUserPw("asdf123456");
        input.setTel("01012341234");
        input.setEmail("test1@naver.com");
        input.setPostcode("12345");
        input.setAddr1("서울특별시 강남구 강남대로94길 13");
        input.setAddr2("삼경빌딩 1층");
        input.setBirthday("2024-11-18");
        input.setIsMarketingAgree("Y");

        int output = memberMapper.insert(input);

        log.debug("output : " + output);
        log.debug("new member : " + input.getMemberId());
        log.debug("Member : " + input.toString());
    }
}
