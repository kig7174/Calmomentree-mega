package com.calmomentree.projectree.services;

import java.util.List;

import com.calmomentree.projectree.models.NewMember;

public interface NewMemberService {
    public void addItem() throws Exception;

    public NewMember getItem(NewMember params) throws Exception;

    public List<NewMember> getWeeklyList() throws Exception;

}
