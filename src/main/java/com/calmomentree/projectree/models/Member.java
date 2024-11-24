package com.calmomentree.projectree.models;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Member implements Serializable {
    private int memberId;              // 회원번호
    private String userName;           // 회원 이름
    private String userId;             // 회원 아이디
    private String userPw;             // 회원 비밀번호
    private String tel;                 // 전화번호
    private String email;               // 이메일
    private String postcode;            // 우편번호
    private String addr1;               // 기본주소
    private String addr2;               // 상세주소
    private String birthday;            // 생년월일
    private String isEmailAgree;      // 이메일 수신 여부
    private String isSmsAgree;        // 문자 메세지 수신 여부
    private String loginDate;          // 마지막 로그인 날짜
    private String joinDate;           // 회원가입 날짜
    private String editDate;           // 마지막 수정 날짜
    private String isOut;              // 탈퇴여부
    private String isAdmin;            // 관리자 여부

    @Getter
    @Setter
    private static int listCount = 0;

    @Getter
    @Setter
    private static int offset = 0;
}