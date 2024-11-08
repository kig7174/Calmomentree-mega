package com.calmomentree.projectree.helpers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component  // <--- 스프링에게 이 클래스가 빈(Bean)임을 알려줌.
public class WebHelper {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;
    
    /**
     * 클라이언트 IP 주소를 가져오는 메서드
     * 
     * @return IP 주소
     */
    public String getClientIp() {
        String ip = request.getHeader("X-Forwarded-For"); 
        if (ip == null) { 
            ip = request.getHeader("Proxy-Client-IP"); 
        } 
        if (ip == null) { 
            ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직 
        } 
        if (ip == null) { 
            ip = request.getHeader("HTTP_CLIENT_IP"); 
        } 
        if (ip == null) { 
            ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
        } 
        if (ip == null) { 
            ip = request.getRemoteAddr(); 
        }

        return ip;
    }

    /**
     * 쿠키값을 저장한다.
     * 
     * @param name - 쿠키 이름
     * @param value - 쿠키 값
     * @param maxAge - 쿠키 유효 시간 (0이면 지정 안함, 음수일 경우 즉시 삭제)
     * @param domain - 쿠키 도메인
     * @param path - 쿠키 경로
     * @throws UnsupportedEncodingException 
     */
    public void writeCookie(String name, String value, int maxAge, String domain, String path) throws Exception {
        if (value != null && !value.equals("")) {
            try {
                value = URLEncoder.encode(value, "utf-8");
            } catch (UnsupportedEncodingException e) {
                log.error("쿠키값 인코딩 실패", e);
                throw e;
            }
        }

        Cookie cookie = new Cookie(name, value);
        cookie.setPath(path);

        if (domain != null) {
            cookie.setDomain(domain);
        }

        if (maxAge != 0) {
            cookie.setMaxAge(maxAge);
        }

        response.addCookie(cookie);
    }

    /**
     * 쿠키값을 저장한다. path값을 "/"로 강제 설정한다.
     * 
     * @param name - 쿠키 이름
     * @param value - 쿠키 값
     * @param maxAge - 쿠키 유효 시간 (0이면 지정 안함, 음수일 경우 즉시 삭제)
     * @param domain - 쿠키 도메인
     * @throws Exception 
     * 
     * @see #writeCookie( String, String, int, String)
     */
    public void writeCookie(String name, String value, int maxAge, String domain) throws Exception {
        this.writeCookie(name, value, maxAge, domain, "/");
    }

    /**
     * 쿠키값을 저장한다. path값을 "/"로, domain을 null로 강제 설정한다.
     * 
     * @param name - 쿠키 이름
     * @param value - 쿠키 값
     * @param maxAge - 쿠키 유효 시간 (0이면 지정 안함, 음수일 경우 즉시 삭제)
     * @throws Exception 
     * 
     * @see #writeCookie(String, String, int)
     */
    public void writeCookie(String name, String value, int maxAge) throws Exception {
        this.writeCookie(name, value, maxAge, null, "/");
    }

    /**
     * 쿠키값을 지정한다. path값을 "/"로, domain을 null로 maxAge를 0으로 강제 설정한다.
     * 쿠키값이 브라우저를 닫기 전까지 유효하다.
     * 
     * @param name - 쿠키 이름
     * @param value - 쿠키 값
     * @throws Exception 
     * 
     * @see #writeCookie(String, String)
     */
    public void writeCookie(String name, String value) throws Exception {
        this.writeCookie(name, value, 0, null, "/");
    }

    /**
     * 쿠키값을 삭제한다.
     * 
     * @param name - 쿠키 이름
     * @throws Exception 
     */
    public void deleteCookie(String name) throws Exception {
        this.writeCookie(name, null, -1, null, "/");
    }

    /**
     * HTTP 상태 코드를 설정하고 메시지를 출력한 후, 지정된 페이지로 이동한다.
     * 이동할 페이지가 없을 경우 이전 페이지로 이동한다.
     * 
     * @param statusCode    - HTTP 상태 코드 (예 : 404)
     * @param url           - 이동할 URL 
     * @param message       - 출력할 메시지
     */
    public void redirect(int statusCode, String url, String message) {
        /** 알림 메시지 표시 후 바로 이전 페이지로 이동 --> Helper에 이식 예정 */
        // HTTP 403 Forbidden 클라이언트 오류 상태 응답 코드는 서버에 요청이 전달외었지만,
        // 권한 때문에 거절되었다는 의미
        response.setStatus(statusCode);
        response.setContentType("text/html; charset=UTF-8");

        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            log.error("응답 출력 스트림 생성 실패", e);
            return;
        }

        if (message != null && !message.equals("")) {
            out.println("<script>");
            out.println("alert('" + message + "');");
            out.println("</script>");
        }

        if (url != null && !url.equals("")) {
            out.println("<meta http-equiv='refresh' content='0; url=" + url + "' />");
        } else {
            out.println("<script>");
            out.println("history.back();");
            out.println("</script>");
        }
        
        out.flush();
    }

    /**
     * HTTP 상태 코드를 200으로 설정하고 메시지 출력한 후, 지정된 페이지로 이동한다.
     * @param url       - 이동할 URL
     * @param message   - 출력할 메시지
     */
    public void redirect(String url, String message) {
        this.redirect(200, url, message);
    }

    /**
     * HTTP 상태코드를 200으로 설정하고 메시지 출력 없이 지정된 페이지로 이동한디.
     * @param url       - 이동할 URL
     */
    public void redirect(String url) {
        this.redirect(200, url, null);
    }

    /**
     * HTTP 상태 코드를 설정하고 메시지 출력 없이 지정된 페이지로 이동한다.
     * @param statusCode - HTTP 상태 코드 (예 : 404)
     * @param url        - 이동할 URL
     */
    public void redirect(int statusCode, String url) {
        this.redirect(statusCode, url, null);
    }

    /**
     * 파라미터가 잘못된 경우에 호출할 이전 페이지 이동 기능
     * @param e         - 에러 정보를 담고 있는 객체.
     *                    Exception으로 선언했으므로 어떤 하위 객체가 전달되더라도 형변환되어 받는다.
     */
    public void badRequest(Exception e) {
        this.redirect(403, null, e.getMessage());
    }

    /**
     * 파라미터가 잘못된 경우에 호출핧 이전 페이지 이동 기능
     * @param message   - 개발자가 직접 전달하는 에러 메시지
     */
    public void badRequest(String message) {
        this.redirect(403, null, message);
    }

    /**
     * JAVA 혹은 SQL쪽에서 잘못된 경우에 호출할 이전 페이지 이동 기능
     * @param e         - 에러 정보를 담고 있는 객체.
     *                    Exception으로 선언했으므로 어떤 하위 객체가 전달되더라도 형변환되어 받는다.
     */
    public void serverError(Exception e) {
        String message = e.getMessage().trim().replace("'", "\\'").split(System.lineSeparator())[0];
        this.redirect(500, null, message);
    }

    /**
     * JAVA 혹은 SQL쪽에서 잘못된 경우에 호출할 이전 페이지 이동 기능
     * @param message   - 개발자가 직접 전달하는 에러 메시지
     */
    public void serverError(String message) {
        this.redirect(500, null, message);
    }
}
