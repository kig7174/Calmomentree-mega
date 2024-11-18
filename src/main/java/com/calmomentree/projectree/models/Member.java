package com.calmomentree.projectree.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Member {
    private int member_id;              // 회원번호
    private String user_name;           // 회원 이름
    private String user_id;             // 회원 아이디
    private String user_pw;             // 회원 비밀번호
    private String tel;                 // 전화번호
    private String email;               // 이메일
    private String postcode;            // 우편번호
    private String addr1;               // 기본주소
    private String addr2;               // 상세주소
    private String birthday;            // 생년월일
    private String is_marketing_agree;  // 마케팅 수신 여부
    private String login_date;          // 마지막 로그인 날짜
    private String join_date;           // 회원가입 날짜
    private String edit_date;           // 마지막 수정 날짜
    private String is_out;              // 탈퇴여부
    private String is_admin;            // 관리자 여부

    @Getter
    @Setter
    private static int listCount = 0;

    @Getter
    @Setter
    private static int offset = 0;
}