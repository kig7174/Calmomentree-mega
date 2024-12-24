package com.calmomentree.projectree.services;

import java.util.List;

import com.calmomentree.projectree.models.NewMember;

public interface NewMemberService {
    public int addItem() throws Exception;

    public int addDefault() throws Exception;

    public List<NewMember> getItem() throws Exception;

    public List<NewMember> getWeeklyList() throws Exception;

}
