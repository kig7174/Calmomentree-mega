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
        input.setUserId("test4");
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

    @Test
    @DisplayName("회원정보수정 테스트")
    void memberModifyTest() throws Exception {
        Member input = new Member();
        input.setMemberId(4);
        input.setUserPw("asdf123456");
        input.setTel("01011223344");
        input.setEmail("test1@naver.com");
        input.setPostcode("12345");
        input.setAddr1("서울특별시 강남구 강남대로94길 13");
        input.setAddr2("삼경빌딩 1층");
        input.setBirthday("2023-11-10");
        input.setIsEmailAgree("N");
        input.setIsSmsAgree("N");

        Member output = null;

        try {
            output = memberService.editItem(input);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        if (output != null) {
            log.debug("output : " + output);
        }
    }

    @Test
    @DisplayName("회원 탈퇴 처리 테스트 (탈퇴여부 관리)")
    void memberIsOutTest() {
        Member input = new Member();
        input.setMemberId(4);
        input.setUserPw("asdf123456");

        int output = 0;

        try {
            output = memberService.out(input);
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        
        if (output != 0) {
            log.debug("output : " + output);
            log.debug("Out Member : " + input);
        }
    }
}