package com.calmomentree.projectree.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.calmomentree.projectree.models.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원가입 테스트")
    void memberJoinTest() throws Exception {
        Member input = new Member();
        input.setUserName("가나다");
        input.setUserId("test2");
        input.setUserPw("asdf123456");
        input.setTel("01012341234");
        input.setEmail("test2@naver.com");
        input.setPostcode("12345");
        input.setAddr1("서울특별시 강남구 강남대로94길 13");
        input.setAddr2("삼경빌딩 1층");
        input.setBirthday("2024-11-19");
        input.setIsEmailAgree("N");
        input.setIsSmsAgree("N");

        Member output = null;

        try {
            output = memberService.addItem(input);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        if (output != null) {
            log.debug("output : " + output.toString());
        }
    }
}