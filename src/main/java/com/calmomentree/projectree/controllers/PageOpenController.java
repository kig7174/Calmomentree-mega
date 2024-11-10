package com.calmomentree.projectree.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/member/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/member/modify")
    public String modify() {
        return "member/modify";
    }

    @GetMapping("/member/id/find_id")
    public String findId() {
        return "member/id/find_id";
    }

    @GetMapping("/member/id/find_password")
    public String findPw() {
        return "member/password/find_password";
    }

    @GetMapping("/product/detail")
    public String detail() {
        return "product/detail";
    }

    @GetMapping("/product/list")
    public String list() {
        return "product/list";
    }

    @GetMapping("/product/search")
    public String search() {
        return "product/search";
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

    @GetMapping("/order/basket")
    public String basket() {
        return "order/basket";
    }

    @GetMapping("/order/order_form")
    public String orderForm() {
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

    @GetMapping("/board/product/list")
    public String productList() {
        return "board/product/list";
    }

    @GetMapping("/board/product/read")
    public String productRead() {
        return "board/product/read";
    }

    @GetMapping("/board/qna/list")
    public String qnaList() {
        return "board/qna/list";
    }

    @GetMapping("/board/qna/read")
    public String qnaRead() {
        return "board/qna/read";
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
}
