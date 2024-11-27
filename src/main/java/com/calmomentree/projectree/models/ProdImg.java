package com.calmomentree.projectree.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ProdImg {
    private int prodImgId;    // 상품 이미지 번호
    private String imgType;    // 상품 이미지 유형 (목록, 상세, 정보)
    private String imgUrl;     // 이미지 경로
    private int prodId;        // 상품 번호 

    @Getter
    @Setter
    private static int listCount = 0;

    @Getter
    @Setter
    private static int offset = 0;
}