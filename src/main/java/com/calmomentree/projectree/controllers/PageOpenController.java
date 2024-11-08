package com.calmomentree.projectree.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageOpenController {
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

    @GetMapping("member/id/find_password")
    public String findPw() {
        return "member/password/find_password";
    }
}
