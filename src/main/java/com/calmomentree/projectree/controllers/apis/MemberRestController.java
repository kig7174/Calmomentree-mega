package com.calmomentree.projectree.controllers.apis;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.calmomentree.projectree.helpers.RestHelper;
import com.calmomentree.projectree.services.MemberService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class MemberRestController {
    @Autowired
    private RestHelper restHelper;
    
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

    ) {

        return null;
    }
}
