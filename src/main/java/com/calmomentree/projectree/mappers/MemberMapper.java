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
            "birthday, is_email_agree, is_sms_agree, " +
            "login_date, join_date, edit_date, " +
            "is_out, is_admin) " +
            "VALUE " +
            "(#{userName}, #{userId}, MD5(#{userPw}), #{tel}, " +
            "#{email}, #{postcode}, #{addr1}, #{addr2}, " +
            "#{birthday}, #{isEmailAgree}, #{isSmsAgree}, " +
            "null, NOW(), NOW(), " +
            "'N', 'N')")
    @Options(useGeneratedKeys = true, keyProperty = "memberId", keyColumn = "member_id")
    public int insert(Member input);

    /**
     * 회원 정보 수정 (modify)
     * 등록된 비밀번호 입력 시 기존 비밀번호 사용 & 회원 정보 수정
     * 새 비밀번호 입력 시 새 비밀번호 등록 & 회원 정보 수정
     * 
     * @param input - 수정할 회원 객체
     * @return - 수정된 회원 수
     */
    @Update("<script> " +
            "UPDATE members SET " + 
            // 입력된 비밀번호가 현재 비밀번호와 다를 때 비밀번호 변경
            // ? 새 비밀번호 입력없이 비밀번호 변경하려면 WHERE절에서 비밀번호가 같은 지 확인하면 안됨
            // ? WHERE절에서 비밀번호가 같은 지 확인을 안해도 되나?
            "user_pw = " +
            "( " +
            "CASE " +
            "WHEN user_pw=MD5(#{userPw}) " +
            "THEN user_pw " +
            "WHEN user_pw!=MD5(#{userPw}) " +
            "THEN MD5(#{userPw}) " +
            "END " +
            "), " +
            // Dynamic SQL if문에서 파라미터값 받는 방법?
            // "<if test='userPw != null and userPw != \"\" and userPw != user_pw'>user_pw=#{userPw},</if> " +
            "tel=#{tel}, " +
            "email=#{email}, " +
            "postcode=#{postcode}, " +
            "addr1=#{addr1}, " +
            "addr2=#{addr2}, " +
            "<if test='birthday != null and birthday != \"\"'>birthday=#{birthday},</if> " +
            "is_email_agree=#{isEmailAgree}, " +
            "is_sms_agree=#{isSmsAgree}, " +
            "edit_date=NOW() " +
            "WHERE member_id=#{memberId} " +
            "</script>")
    public int update(Member input);

    @Delete("DELETE FROM members WHERE member_id=#{memberId}")
    public int delete(Member input);

    @Select("SELECT " +
            "member_id, user_name, user_id, user_pw, " +
            "tel, email, postcode, addr1, addr2, " +
            "birthday, is_email_agree, is_sms_agree, " +
            "login_date, join_date, edit_date, " +
            "is_out, is_admin " +
            "FROM members " +
            "WHERE member_id=#{memberId}")
    @Results(id = "memberMap", value = {
        @Result(property="memberId", column="member_id"),
        @Result(property="userName", column="user_name"),
        @Result(property="userId", column="user_id"),
        @Result(property="userPw", column="user_pw"),
        @Result(property="tel", column="tel"),
        @Result(property="email", column="email"),
        @Result(property="postcode", column="postcode"),
        @Result(property="addr1", column="addr1"),
        @Result(property="addr2", column="addr2"),
        @Result(property="birthday", column="birthday"),
        @Result(property="isEmailAgree", column="is_email_agree"),
        @Result(property="isSmsAgree", column="is_sms_agree"),
        @Result(property="loginDate", column="login_date"),
        @Result(property="joinDate", column="join_date"),
        @Result(property="editDate", column="edit_date"),
        @Result(property="isOut", column="is_out"),
        @Result(property="isAdmin", column="is_admin")
    })
    public Member selectItem(Member input);

    @Select("SELECT " +
            "member_id, user_name, user_id, user_pw, " +
            "tel, email, postcode, addr1, addr2, " +
            "birthday, is_email_agree, is_sms_agree, " +
            "login_date, join_date, edit_date, " +
            "is_out, is_admin " +
            "FROM members")
    @ResultMap("memberMap")
    public List<Member> selectList(Member input);

    @Select("<script> " +
            "SELECT COUNT(*) FROM members " +
            "<where> " +
            "<if test='userId != null'>user_id = #{userId}</if> " +
            "<if test='memberId != 0'>AND member_id != #{memberId}</if>" +
            "</where> " +
            "</script>")
    public int selectCount(Member input);

    @Select("SELECT " +
            "member_id, user_name, user_id, user_pw, " +
            "tel, email, postcode, addr1, addr2, " +
            "birthday, is_email_agree, is_sms_agree, " +
            "login_date, join_date, edit_date, " +
            "is_out, is_admin " +
            "FROM members " +
            "WHERE user_id = #{userId} AND user_pw = MD5(#{userPw}) AND is_out = 'N'")
    @ResultMap("memberMap")
    public Member login (Member input);

    @Update("UPDATE members SET " +
            "login_date = NOW() " +
            "WHERE member_id=#{memberId} AND is_out = 'N'")
    public int updateLoginDate (Member input);

    /**
     * 회원 탈퇴
     * 탈퇴 여부가 'Y'로 변경될 뿐 실제 탈퇴 처리는 이루어지지 않음
     * 
     * @param input
     * @return
     */
    @Update("UPDATE members SET " +
            "is_out='Y', edit_date=NOW() " +
            "WHERE member_id=#{memberId} AND user_pw=MD5(#{userPw}) AND is_out = 'N'")
    public int out(Member input);

    /**
     * 탈퇴 회원 일괄 처리
     * Scheduler로 관리
     * 
     * @return
     */
    @Delete("DELETE FROM members " +
            "WHERE is_out='Y' AND " +
            "edit_date < DATE_ADD(NOW(), INTERVAL -3 month)")
    public int deleteOutMembers();
}