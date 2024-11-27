package com.calmomentree.projectree.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.calmomentree.projectree.exceptions.StringFormatException;
import com.calmomentree.projectree.helpers.RegexHelper;
import com.calmomentree.projectree.helpers.UtilHelper;
import com.calmomentree.projectree.helpers.WebHelper;
import com.calmomentree.projectree.models.Member;
import com.calmomentree.projectree.services.MemberService;

@Controller
public class MemberController {

    @Autowired
    private RegexHelper regexHelper;

    @Autowired
    private WebHelper webHelper;

    @Autowired
    private UtilHelper utilHelper;

    @Autowired
    private MemberService memberService;

    @GetMapping("/member/id/find_id_result")
    public String findId(
        Model model,
        @RequestParam("user_name") String userName,
        @RequestParam("email") String email
    ) {
        try {            
            regexHelper.isValue(userName, "이름을 입력해주세요.");
            regexHelper.isKor(userName, "이름은 한글로만 입력해주세요.");

            regexHelper.isValue(email, "이메일을 입력해주세요.");
            regexHelper.isEmail(email, "이메일 형식이 잘못되었습니다.");
        } catch (StringFormatException e) {
            webHelper.badRequest(e);
            return null;
        }

        Member input = new Member();
        input.setUserName(userName);
        input.setEmail(email);

        Member output = null;

        try {
            output = memberService.findId(input);
        } catch (Exception e) {
            webHelper.serverError(e);
            return null;
        }
        
        model.addAttribute("item", output);

        return "member/id/find_id_result";
    }

    @PostMapping("/member/password/reset_pw_result")
    public String resetPw(
        Model model,
        @RequestParam("user_id") String userId,
        @RequestParam("user_name") String userName,
        @RequestParam("email") String email
    ) {
        try {
            regexHelper.isValue(userId, "아이디를 입력해주세요.");
            regexHelper.isLowerEngNum(userId, "아이디는 영문소문자와 숫자로 입력해주세요.");

            regexHelper.isValue(userName, "이름을 입력해주세요.");
            regexHelper.isKor(userName, "이름은 한글로만 입력해주세요.");

            regexHelper.isValue(email, "이메일을 입력해주세요.");
            regexHelper.isEmail(email, "이메일 형식이 잘못되었습니다.");
        } catch (StringFormatException e) {
            webHelper.badRequest(e);
            return null;
        }

        String newPw = utilHelper.randomPassword(10);
        Member input = new Member();
        input.setUserId(userId);
        input.setUserName(userName);
        input.setEmail(email);
        input.setUserPw(newPw);

        try {
            memberService.resetPw(input);
        } catch (Exception e) {
            webHelper.serverError(e);
            return null;
        }

        model.addAttribute("item", input);
        model.addAttribute("password", newPw);

        return "/member/password/reset_pw_result";
    }
}
