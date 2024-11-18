package com.calmomentree.projectree.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.calmomentree.projectree.models.Member;

@Mapper
public interface MemberMapper {
    
    /**
     * 회원 정보 입력 (회원가입 & join)
     * 
     * @param input - 가입할 회원 객체
     * @return - 가입된 회원 수
     */
    @Insert("INSERT INTO members " +
            "(user_name, user_id, user_pw, tel, " + 
            "email, postcode, addr1, addr2, " + 
            "birthday, is_marketing_agree, " +
            "login_date, join_date, edit_date, " +
            "is_out, is_admin) " +
            "VALUE " +
            "(#{userName}, #{userId}, MD5(#{userPw}), #{tel}, " +
            "#{email}, #{postcode}, #{addr1}, #{addr2}, " +
            "#{birthday}, #{isMarketingAgree}, " +
            "null, NOW(), NOW(), " +
            "'N', 'N')")
    @Options(useGeneratedKeys = true, keyProperty = "memberId", keyColumn = "member_id")
    public int insert(Member input);

    @Update("...")
    public int update(Member input);

    @Delete("...")
    public int delete(Member input);

    @Select("...")
    @Results(id="resultMap", value={
        @Result(property="...", column="..."),
        @Result(property="...", column="..."),
        @Result(property="...", column="...")
    })
    public Member selectItem(Member input);

    @Select("...")
    @ResultMap("resultMap")
    public List<Member> selectList(Member input);

    @Select("...")
    public int selectCount(Member input);
}

