package com.calmomentree.projectree.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calmomentree.projectree.mappers.MemberMapper;
import com.calmomentree.projectree.models.Member;
import com.calmomentree.projectree.services.MemberService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Member addItem(Member input) throws Exception {
        int rows = 0;

        try {
            rows = memberMapper.insert(input);

            if (rows == 0) {
                throw new Exception("저장된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 저장에 실패했습니다.", e);
            throw e;
        }

        return memberMapper.selectItem(input);
    }

    @Override
    public Member editItem(Member input) throws Exception {
        int rows = 0;

        try {
            rows = memberMapper.update(input);

            if (rows == 0) {
                throw new Exception("수정된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 수정에 실패했습니다.", e);
            throw e;
        }

        return memberMapper.selectItem(input);
    }

    @Override
    public int deleteItem(Member input) throws Exception {
        int rows = 0;

        try {
            rows = memberMapper.delete(input);

            if (rows == 0) {
                throw new Exception("삭제된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("데이터 삭제에 실패했습니다.", e);
            throw e;
        }

        return rows;
    }

    @Override
    public Member getItem(Member input) throws Exception {
        Member output = null;

        try {
            output = memberMapper.selectItem(input);

            if (output == null) {
                throw new Exception("조회된 데이터가 없습니다.");
            }
        } catch (Exception e) {
            log.error("회원 조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public List<Member> getList(Member input) throws Exception {
        List<Member> output = null;

        try {
            output = memberMapper.selectList(input);
        } catch (Exception e) {
            log.error("회원 목록 조회에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public int getCount(Member input) throws Exception {
        int output = 0;

        try {
            output = memberMapper.selectCount(input);
        } catch (Exception e) {
            log.error("데이터 집계에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public int out(Member input) throws Exception {
        int output = 0;

        try {
            output = memberMapper.out(input);

            if (output == 0) {
                throw new Exception("비밀번호 확인이 잘못되었거나 존재하지 않는 회원입니다.");
            }
        } catch (Exception e) {
            log.error("탈퇴 처리에 실패했습니다.", e);
            throw e;
        }

        return output;
    }

    @Override
    public void isUniqueUserId(String userid) throws Exception {
        Member input = new Member();
        input.setUserId(userid);

        int output = 0;

        try {
            output = memberMapper.selectCount(input);

            if (output > 0) {
                throw new Exception("는 사용할 수 없는 아이디 입니다.");
            }
        } catch (Exception e) {
            log.error("아이디 중복검사에 실패했습니다.", e);
            throw e;
        }
    }

    @Override
    public Member login(Member input) throws Exception {
        Member output = null;

        try {
            output = memberMapper.login(input);

            if (output == null) {
                throw new Exception("아이디 혹은 비밀번호를 확인해주세요.");
            }
        } catch (Exception e) {
            log.error("Member 데이터 조회에 실패했습니다.", e);
            throw e;
        }

        try {
            int rows = memberMapper.updateLoginDate(output);

            if (rows == 0 || rows > 1) {
                throw new Exception("존재하지 않는 회원에 대한 요청입니다.");
            }
        } catch (Exception e) {
            log.error("Member 데이터 갱신에 실패했습니다.", e);
            throw e;
        }

        return output;
    }
}