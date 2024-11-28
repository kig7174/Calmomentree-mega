package com.calmomentree.projectree.controllers.apis;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.calmomentree.projectree.exceptions.StringFormatException;
import com.calmomentree.projectree.helpers.RegexHelper;
import com.calmomentree.projectree.helpers.RestHelper;
import com.calmomentree.projectree.helpers.WebHelper;
import com.calmomentree.projectree.models.Member;
import com.calmomentree.projectree.services.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class MemberRestController {
    @Autowired
    private RestHelper restHelper;

    @Autowired
    private RegexHelper regexHelper;

    @Autowired
    private WebHelper webHelper;

    @Autowired
    private MemberService memberService;

    /**
     * 아이디 중복 검사
     * 
     * @param userId - 입력한 아이디
     * @return       - 중복되지 않으면 메세지 리턴
     */
    @GetMapping("/api/member/id_unique_check")
    public Map<String, Object> idUniqueCheck(@RequestParam ("user_id") String userId) {
        try {
            memberService.isUniqueUserId(userId);
        } catch (Exception e) {
            return restHelper.badRequest(e);
        }

        return restHelper.sendJson(200, "는 사용 가능한 아이디입니다.", null, null);
    }
    
    /**
     * 회원 가입 처리
     * 
     * @return
     */
    @PostMapping("/api/member/join")
    public Map<String, Object> join(  
        @RequestParam("user_id") String userId,
        @RequestParam("user_pw") String userPw,
        @RequestParam("user_name") String userName,
        @RequestParam("pw_confirm") String pwConfirm,
        @RequestParam("tel1") String tel1,
        @RequestParam("tel2") String tel2,
        @RequestParam("tel3") String tel3,
        @RequestParam("email") String email,
        @RequestParam("postcode") String postcode,
        @RequestParam("addr1") String addr1,
        @RequestParam("addr2") String addr2,
        @RequestParam(value = "birthday", required = false) String birthday,
        @RequestParam("is_sms_agree") String isSmsAgree,
        @RequestParam("is_email_agree") String isEmailAgree,
        @RequestParam("agree_service_check") String agreeServiceCheck,
        @RequestParam("agree_privacy_check") String agreePrivacyCheck
    ) {
        /** 전화번호 결합 */
        String tel = tel1 + tel2 + tel3;

        /** 유효성 검사 */
        try {
            regexHelper.isValue(userId, "아이디를 입력해주세요.");
            regexHelper.isLowerEngNum(userId, "아이디는 영문소문자와 숫자로 입력해주세요.");

            regexHelper.isValue(userPw, "비밀번호를 입력해주세요.");
            regexHelper.isPassword(userPw, "비밀번호 형식이 잘못되었습니다.");
            regexHelper.isMatch(userPw, pwConfirm, "비밀번호확인이 일치하지 않습니다.");
            
            regexHelper.isValue(userName, "이름을 입력해주세요.");
            regexHelper.isKor(userName, "이름은 한글로만 입력해주세요.");

            regexHelper.isValue(tel, "전화번호를 입력해주세요.");
            regexHelper.isPhone(tel, "전화번호 형식이 잘못되었습니다.");

            regexHelper.isValue(email, "이메일을 입력해주세요.");
            regexHelper.isEmail(email, "이메일 형식이 잘못되었습니다.");

            regexHelper.isValue(postcode, "우편번호를 검색해주세요.");
            regexHelper.isNum(postcode, "우편번호는 숫자로만 입력해주세요.");
            regexHelper.isValue(addr1, "주소를 검색해주세요.");
            regexHelper.isValue(addr2, "상세주소를 입력해주세요.");

            regexHelper.isMatch(agreeServiceCheck, "Y", "이용약관 동의는 필수 항목입니다.");
            regexHelper.isMatch(agreePrivacyCheck, "Y", "개인정보처리방침 약관 동의는 필수 항목입니다.");
        } catch (StringFormatException e) {
            return restHelper.badRequest(e);
        }

        /** 아이디 중복 검사 */
        try {
            memberService.isUniqueUserId(userId);
        } catch (Exception e) {
            return restHelper.badRequest(e);
        }

        /** 전달할 객체 구성 */
        Member input = new Member();
        input.setUserId(userId);
        input.setUserPw(userPw);
        input.setUserName(userName);
        input.setTel(tel);
        input.setEmail(email);
        input.setPostcode(postcode);
        input.setAddr1(addr1);
        input.setAddr2(addr2);
        input.setBirthday(birthday);
        input.setIsSmsAgree(isSmsAgree);
        input.setIsEmailAgree(isEmailAgree);

        /** 전송 */
        try {
            memberService.addItem(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        return restHelper.sendJson();
    }

    @PostMapping("/api/member/login")
    public Map<String, Object> login(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestParam("user_id") String userId,
        @RequestParam("user_pw") String userPw,
        @RequestParam(value = "remember_id", defaultValue = "N") String rememberId
    ) {
        /** 유효성 검사 */
        try {
            regexHelper.isValue(userId, "아이디를 입력해주세요.");
            regexHelper.isLowerEngNum(userId, "아이디는 영문소문자와 숫자로 입력해주세요.");

            regexHelper.isValue(userPw, "비밀번호를 입력해주세요.");
            regexHelper.isPassword(userPw, "비밀번호 형식이 잘못되었습니다.");
        } catch (StringFormatException e) {
            return restHelper.badRequest(e);
        }
        /** 객체 구성 */
        Member input = new Member();
        input.setUserId(userId);
        input.setUserPw(userPw);
        
        /** 로그인 시도 */
        Member output = null;

        try {
            output = memberService.login(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        /** 세션 저장 */
        HttpSession session = request.getSession();
        session.setAttribute("memberInfo", output);

        if (rememberId.equals("Y")) {
            try {
                webHelper.writeCookie("rememberId", userId, 60 * 60 * 24 * 7, "/member/login");
            } catch (Exception e) {
                return restHelper.serverError("아이디 저장 실패");
            }
        }

        return restHelper.sendJson();
    }

    @DeleteMapping("/api/member/out")
    public Map<String, Object> memberOut(
        HttpServletRequest request,
        @SessionAttribute("memberInfo") Member memberInfo,
        @RequestParam("user_pw") String userPw
    ) {
        memberInfo.setUserPw(userPw);

        try {
            memberService.out(memberInfo);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        HttpSession session = request.getSession();
        session.invalidate();

        return restHelper.sendJson();
    }

    @PutMapping("/api/member/modify")
    public Map<String, Object> modify(
        HttpServletRequest request,
        @SessionAttribute("memberInfo") Member memberInfo,
        @RequestParam("user_pw") String userPw,
        @RequestParam("pw_confirm") String pwConfirm,
        @RequestParam("tel") String tel,
        @RequestParam("email") String email,
        @RequestParam("postcode") String postcode,
        @RequestParam("addr1") String addr1,
        @RequestParam("addr2") String addr2,
        @RequestParam(value = "birthday", required = false) String birthday,
        @RequestParam("is_sms_agree") String isSmsAgree,
        @RequestParam("is_email_agree") String isEmailAgree
        
    ) {
        try {
            regexHelper.isValue(userPw, "비밀번호를 입력해주세요.");
            regexHelper.isPassword(userPw, "비밀번호 형식이 잘못되었습니다.");
            regexHelper.isMatch(userPw, pwConfirm, "비밀번호확인이 일치하지 않습니다.");

            regexHelper.isValue(tel, "전화번호를 입력해주세요.");
            regexHelper.isPhone(tel, "전화번호 형식이 잘못되었습니다.");
            
            regexHelper.isValue(email, "이메일을 입력해주세요.");
            regexHelper.isEmail(email, "이메일 형식이 잘못되었습니다.");

            regexHelper.isValue(postcode, "우편번호를 검색해주세요.");
            regexHelper.isNum(postcode, "우편번호는 숫자로만 입력해주세요.");
            regexHelper.isValue(addr1, "주소를 검색해주세요.");
            regexHelper.isValue(addr2, "상세주소를 입력해주세요.");

        } catch (StringFormatException e) {
            return restHelper.badRequest(e);
        }

        Member member = new Member();
        member.setMemberId(memberInfo.getMemberId());
        member.setUserPw(userPw);
        member.setTel(tel);
        member.setEmail(email);
        member.setPostcode(postcode);
        member.setAddr1(addr1);
        member.setAddr2(addr2);
        member.setBirthday(birthday);
        member.setIsSmsAgree(isSmsAgree);
        member.setIsEmailAgree(isEmailAgree);

        Member output = null;

        try {
            output = memberService.editItem(member);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        request.getSession().setAttribute("memberInfo", output);

        return restHelper.sendJson();
    }

    @GetMapping("/member/logout")
    public Map<String, Object> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return restHelper.sendJson();
    }
}