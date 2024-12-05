package com.calmomentree.projectree.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PageOpenController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/sustainable")
    public String sustain() {
        return "sustainable";
    }

    @GetMapping("/co-promotion")
    public String coPro() {
        return "co-promotion";
    }

    @GetMapping("/member/join")
    public String join() {
        return "member/join";
    }

    @GetMapping("/member/mall_agreement")
    public String mailAgreement() {
        return "member/mall_agreement";
    }

    @GetMapping("/member/privacy")
    public String privacy() {
        return "member/privacy";
    }

    @GetMapping("/member/modify")
    public String modify() {
        return "member/modify";
    }

    @GetMapping("/member/id/find_id")
    public String findId() {
        return "member/id/find_id";
    }

    @GetMapping("/product/detail")
    public String detail() {
        return "product/detail";
    }

    @GetMapping("/product/list")
    public String list() {
        return "product/list";
    }

    @GetMapping("/myshop")
    public String myshop() {
        return "myshop/index";
    }

    @GetMapping("/myshop/board_list")
    public String boardList() {
        return "myshop/board_list";
    }

    @GetMapping("/myshop/order/list")
    public String orderList() {
        return "myshop/order/list";
    }

    @GetMapping("/order/search_board_list")
    public String searchOrder() {
        return "order/search_board_list";
    }

    @GetMapping("/order/order_form")
    public String order() {
        return "order/order_form";
    }
    

    @GetMapping("/board/faq/list")
    public String faqList() {
        return "board/faq/list";
    }

    @GetMapping("/board/faq/read")
    public String faqRead() {
        return "board/faq/read";
    }

    @GetMapping("/board/notice/list")
    public String noticeList() {
        return "board/notice/list";
    }

    @GetMapping("/board/notice/read")
    public String noticeRead() {
        return "board/notice/read";
    }

    @GetMapping("/board/product/read")
    public String productRead() {
        return "board/product/read";
    }

    @GetMapping("/board/product/modify")
    public String productModify() {
        return "board/product/modify";
    }
    @GetMapping("/board/product/write")
    public String productWrite() {
        return "board/product/write";
    }

    @GetMapping("/board/qna/write")
    public String qnaWrite() {
        return "board/qna/write";
    }

    @GetMapping("/board/recycling/list")
    public String recyList() {
        return "board/recycling/list";
    }

    @GetMapping("/board/recycling/write")
    public String recyWrite() {
        return "board/recycling/write";
    }

    @GetMapping("/board/free/read")
    public String freeRead() {
        return "board/free/read";
    }

    @GetMapping("/board/amenity/list")
    public String amenityList() {
        return "board/amenity/list";
    }

    @GetMapping("/board/amenity/write")
    public String amenityWrite() {
        return "board/amenity/write";
    }

    @GetMapping("/board/amenity/read")
    public String amenityRead() {
        return "board/amenity/read";
    }
    
}
