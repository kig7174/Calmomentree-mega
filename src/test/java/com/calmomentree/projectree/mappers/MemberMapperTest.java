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
        input.setIsEmailAgree("N");
        input.setIsSmsAgree("N");

        int output = memberMapper.insert(input);

        log.debug("output : " + output);
        log.debug("new member : " + input.getMemberId());
        log.debug("Member : " + input.toString());
    }

    @Test
    @DisplayName("회원정보수정 테스트")
    void memberModifyTest() {
        Member input = new Member();
        input.setMemberId(2);
        input.setUserPw("asdf1234567");
        input.setTel("01012341234");
        input.setEmail("test1@naver.com");
        input.setPostcode("12345");
        input.setAddr1("서울특별시 강남구 강남대로94길 13");
        input.setAddr2("삼경빌딩 1층");
        input.setBirthday("2024-11-10");
        input.setIsEmailAgree("N");
        input.setIsSmsAgree("N");

        int output = memberMapper.update(input);

        log.debug("output : " + output);
        log.debug("Member : " + input.toString());
    }

    @Test
    @DisplayName("회원 탈퇴 처리 테스트 (탈퇴여부 관리)")
    void memberIsOutTEst() {
        Member input = new Member();
        input.setMemberId(2);
        input.setUserPw("asdf1234567");

        int output = memberMapper.out(input);

        log.debug("output : " + output);
        log.debug("Member_is_out : " + input.getIsOut());
    }

    @Test
    @DisplayName("탈퇴 회원 일괄 처리 테스트")
    void deleteOutMembersTest() {
        int output = memberMapper.deleteOutMembers();

        log.debug("Out Members : " + output);
    }
}
