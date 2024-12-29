package com.calmomentree.projectree.controllers.apis;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.calmomentree.projectree.helpers.RestHelper;
import com.calmomentree.projectree.models.NewMember;
import com.calmomentree.projectree.services.NewMemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RestController
@Tag(name = "new_member", description = "신규 회원 추이 API")
public class NewMemberRestController {
    @Autowired
    private NewMemberService newMemberService;

    @Autowired
    private RestHelper restHelper;
    
    @GetMapping("/api/new_member")
    @Operation(summary = "신규 회원 추이 데이터", description = "신규 회원 추이를 json파일로 전송.")
    public Map<String, Object> newMember() {
        List<NewMember> newMembersweekly = null;
        List<NewMember> newMembersMonthly = null;
        try {
            newMembersweekly = newMemberService.getItem();
            newMembersMonthly = newMemberService.getWeeklyList();
        } catch (Exception e) {
            return restHelper.serverError(e);
        }
        
        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("weekly", newMembersweekly);
        data.put("monthly", newMembersMonthly);

        return restHelper.sendJson(data);
    }
}
