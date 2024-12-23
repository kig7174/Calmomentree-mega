package com.calmomentree.projectree.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calmomentree.projectree.mappers.NewMemberMapper;
import com.calmomentree.projectree.models.NewMember;
import com.calmomentree.projectree.services.NewMemberService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NewMemberServiceImpl implements NewMemberService {

    @Autowired
    private NewMemberMapper newMemberMapper;

    @Override
    public void addItem() throws Exception {

        try {
            newMemberMapper.insert();

        } catch (Exception e) {
            log.error("데이터 저장에 실패했습니다.", e);
            throw e;
        }
    }


    @Override
    public NewMember getItem(NewMember input) throws Exception {
        NewMember output = null;

        try {
            output = newMemberMapper.selectItem(input);

            if (output == null) {
                throw new Exception("조회된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("신규 회원 조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public List<NewMember> getWeeklyList() throws Exception {
        List<NewMember> output = null;

        try {
            output = newMemberMapper.selectWeekly();
        } catch (Exception e) {
            log.error("신규 회원 목록 조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

  
}