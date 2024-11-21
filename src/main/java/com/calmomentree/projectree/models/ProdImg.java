package com.calmomentree.projectree.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ProdImg {
    private int prod_img_id;    // 상품 이미지 번호
    private String img_type;    // 상품 이미지 유형 (목록, 상세, 정보)
    private String img_url;     // 이미지 경로
    private int prod_id;        // 상품 번호 

    @Getter
    @Setter
    private static int listCount = 0;

    @Getter
    @Setter
    private static int offset = 0;
}