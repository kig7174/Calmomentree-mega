package com.calmomentree.projectree.controllers.apis;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.calmomentree.projectree.helpers.RestHelper;
import com.calmomentree.projectree.models.NewMember;
import com.calmomentree.projectree.services.NewMemberService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@RestController
public class NewMemberRestController {
    @Autowired
    private NewMemberService newMemberService;

    @Autowired
    private RestHelper restHelper;
    
    @GetMapping("/api/new_member")
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
