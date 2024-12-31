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
    public int addItem(NewMember input) throws Exception {
        int rows = 0;
        try {
        rows =  newMemberMapper.insert(input);

        } catch (Exception e) {
            log.error("데이터 저장에 실패했습니다.", e);
            throw e;
        }
        return rows;
    }

    
    @Override
    public int addDefault(NewMember input) throws Exception {
        int rows = 0;
        try {
        rows =  newMemberMapper.insertDefault(input);

        } catch (Exception e) {
            log.error("데이터 저장에 실패했습니다.", e);
            throw e;
        }
        return rows;
    }

    // @Override
    // public int autoDelete() throws Exception {
    //     int rows = 0;
    //     try {
    //     rows =  newMemberMapper.autoDelete();

    //     } catch (Exception e) {
    //         log.error("데이터 삭제에 실패했습니다.", e);
    //         throw e;
    //     }
    //     return rows;
    // }

    @Override
    public List<NewMember> getWeeklyList() throws Exception {
        List<NewMember> output = null;

        try {
            output = newMemberMapper.selectWeekly();

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
    public List<NewMember> getMonthlyList() throws Exception {
        List<NewMember> output = null;

        try {
            output = newMemberMapper.selectMonthly();
        } catch (Exception e) {
            log.error("신규 회원 목록 조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    } 
}