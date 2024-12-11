package com.calmomentree.projectree.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Order {
    private int orderId;            // 주문 일련번호
    private String orderNo;         // 주문 번호
    private String orderState;      // 주문 상태
    private String orderDate;       // 주문 날짜
    private String memberName;      // 주문자 이름
    private String memberEmail;     // 주문자 이메일
    private String memberPostcode;     // 주문자 우편번호
    private String memberAddr1;     // 주문자 기본주소
    private String memberAddr2;     // 주문자 상세주소
    private String memberTel;       // 주문자 전화번호
    private String receiverName;    // 받는 사람 이름
    private String receiverPostcode;   // 받는 사람 우편번호
    private String receiverAddr1;   // 받는 사람 기본주소
    private String receiverAddr2;   // 받는 사람 상세주소
    private String receiverTel;     // 받는 사람 전화번호
    private int totalPrice;         // 총 결제 가격
    private String req;             // 배송요청사항
    private int memberId;           // 결제한 회원번호

    private int prodId;             // 상품번호
    private int quantity;           // 수량
    private int orderPrice;         // 상품 금액
    private String prodNameKor;     // 상품 이름
    private String imgUrl;          // 상품 사진 URL

    private String regDate;         // 등록일시
    private String editDate;        // 변경일시

    @Getter
    @Setter
    private static int count;

    public static void orderCountReset() {
        count = 0;
    }

    public static void orderCount() {
        count++;
    }

    @Getter
    @Setter
    private static int listCount = 0;

    @Getter
    @Setter
    private static int offset = 0;
}