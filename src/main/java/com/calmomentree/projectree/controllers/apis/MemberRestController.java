package com.calmomentree.projectree.controllers.apis;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.calmomentree.projectree.exceptions.StringFormatException;
import com.calmomentree.projectree.helpers.RegexHelper;
import com.calmomentree.projectree.helpers.RestHelper;
import com.calmomentree.projectree.models.Member;
import com.calmomentree.projectree.services.MemberService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class MemberRestController {
    @Autowired
    private RestHelper restHelper;

    @Autowired
    private RegexHelper regexHelper;
    
    @Autowired
    private MemberService memberService;

    @GetMapping("/api/member/id_unique_check")
    public Map<String, Object> idUniqueCheck(@RequestParam ("user_id") String userId) {
        try {
            memberService.isUniqueUserId(userId);
        } catch (Exception e) {
            return restHelper.badRequest(e);
        }

        return restHelper.sendJson(200, "는 사용 가능한 아이디입니다.", null, null);
    }
    
    @PostMapping("/api/member/join")
    public Map<String, Object> join(  
        @RequestParam("user_id") String userId,
        @RequestParam("user_pw") String userPw,
        @RequestParam("user_name") String userName,
        @RequestParam("tel1") String tel1,
        @RequestParam("tel2") String tel2,
        @RequestParam("tel3") String tel3,
        @RequestParam("email") String email,
        @RequestParam("postcode") String postcode,
        @RequestParam("addr1") String addr1,
        @RequestParam("addr2") String addr2,
        @RequestParam(value = "birthday", required = false) String birthday,
        @RequestParam("is_sms_agree") String isSmsAgree,
        @RequestParam("is_email_agree") String isEmailAgree
    ) {
        String tel = tel1 + tel2 + tel3;

        try {
            regexHelper.isValue(userId, "아이디를 입력해주세요.");
            
        } catch (StringFormatException e) {
            return restHelper.badRequest(e);
        }

        Member input = new Member();
        input.setUserId(userId);
        input.setUserPw(userPw);
        input.setUserName(userName);
        input.setTel(tel);
        input.setEmail(email);
        input.setPostcode(postcode);
        input.setAddr1(addr1);
        input.setAddr2(addr2);
        input.setBirthday(birthday);
        input.setIsSmsAgree(isSmsAgree);
        input.setIsEmailAgree(isEmailAgree);

        try {
            memberService.addItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        return restHelper.sendJson();
    }
}
