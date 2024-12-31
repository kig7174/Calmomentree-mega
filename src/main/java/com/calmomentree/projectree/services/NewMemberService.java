package com.calmomentree.projectree.services;

import java.util.List;

import com.calmomentree.projectree.models.NewMember;

public interface NewMemberService {
    public int addItem(NewMember input) throws Exception;

    public int addDefault(NewMember input) throws Exception;

    // public int autoDelete() throws Exception;

    public List<NewMember> getWeeklyList() throws Exception;

    public List<NewMember> getMonthlyList() throws Exception;

}
