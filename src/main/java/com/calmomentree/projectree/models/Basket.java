package com.calmomentree.projectree.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Basket {
    private int basketId;         // 장바구니 고유번호
    private int quantity;         // 수량
    private String basketAddDate; // 장바구니 추가 날짜

    private int prodId;           // 상품번호
    private int memberId;         // 회원번호

    private String prodNameKor;
    private int capacity;
    private int price;

    private String regDate;
    private String editDate;

    @Getter
    @Setter
    private static int listCount = 0;

    @Getter
    @Setter
    private static int offset = 0;
}