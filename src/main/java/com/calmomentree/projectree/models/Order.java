package com.calmomentree.projectree.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Order {
    private int orderId;        // 주문 일련번호
    private String orderNo;     // 주문 번호
   private String orderState;   // 주문 상태
   private String orderDate;    // 주문 날짜
   private String memberName;   // 주문자 이름
   private String memberEmail;  // 주문자 이메일
   private int memberPostcode;  // 주문자 우편번호
   private String memberAddr1;  // 주문자 기본주소
   private String memberAddr2;  // 주문자 상세주소
   private String memberTel;    // 주문자 전화번호
   private String receiverName; // 받는 사람 이름
   private int receiverPostcode; // 받는 사람 우편번호
    
    

    private String regDate;
    private String editDate;

    @Getter
    @Setter
    private static int listCount = 0;

    @Getter
    @Setter
    private static int offset = 0;
}