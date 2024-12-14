package com.calmomentree.projectree.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class OrderItem {
    private int orderItemId;    // 주문상품정보 번호
    private String prodName;    // 상품 이름
    private int orderPrice;     // 주문 금액
    private int orderQuantity;  // 주문 수량
    private int prodId;         // 상품 번호
    private int orderId;        // 주문 일련번호

    // private String imgUrl;

    private String regDate;
    private String editDate;

    @Getter
    @Setter
    private static int listCount = 0;

    @Getter
    @Setter
    private static int offset = 0;
}