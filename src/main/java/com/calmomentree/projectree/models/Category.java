package com.calmomentree.projectree.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Category {
    private int category_id;            // 카테고리 번호
    private String category_name;       // 카테고리 이름
    private String parent_category_no;  // 부모카테고리 번호

    @Getter
    @Setter
    private static int listCount = 0;

    @Getter
    @Setter
    private static int offset = 0;
}