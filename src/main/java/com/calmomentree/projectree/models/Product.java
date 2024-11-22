package com.calmomentree.projectree.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Product { 
    private int prodId; 			// 상품번호   
    private String prodNameKor;	    // 한글 상품명
    private String prodNameEng;	    // 영문 상품명
    private String funcTxt;		    // 기능 텍스트
    private String descTxt;		    // 상세 텍스트
    private int price;				// 가격         
    private String isDiscount;    	// 할인 여부   
    private int discount;          	// 할인율    
    private String capacity;       	// 용량   
    private String specification;   // 사양
    private String usePeriod;     	// 사용기간
    private String useMethod;     	// 사용방법
    private String manufacturer;   	// 제조사
    private String releaseDate;   	// 출시 날짜  
    private String editDate;      	// 수정 날짜
    private int categoryId;       	// 카테고리 번호  

    private String categoryName;    // 상품 카테고리
    private String parentCategoryName; // 상위 카테고리
    private int parentCategoryNo;   // 상위 카테고리 번호


    @Getter
    @Setter
    private static int listCount = 0;

    @Getter
    @Setter
    private static int offset = 0;
}