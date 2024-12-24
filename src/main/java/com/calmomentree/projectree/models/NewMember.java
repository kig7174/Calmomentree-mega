package com.calmomentree.projectree.models;

import lombok.Data;

@Data
public class NewMember {
    private int id;             // 신규 회원 번호
    private String date;        // 날짜(가입일)
    private int memberCount;    // 신규 회원 수

    private String week;
    private String day;
    // private String weekOfMonth;
}
