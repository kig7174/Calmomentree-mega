package com.calmomentree.projectree.services;

import java.util.List;

import com.calmomentree.projectree.models.Member;

public interface MemberService {
    public Member addItem(Member params) throws Exception;

    public Member editItem(Member params) throws Exception;

    public int deleteItem(Member params) throws Exception;

    public Member getItem(Member params) throws Exception;

    public List<Member> getList(Member params) throws Exception;

    public int getCount(Member params) throws Exception;

    public int out(Member params) throws Exception;

    public void isUniqueUserId(String userid) throws Exception;
}
