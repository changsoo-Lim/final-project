package kr.or.ddit.security.authenticate;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import kr.or.ddit.security.UsersVOWrapper;
import kr.or.ddit.vo.UsersVO;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        String lvn = null;
        if (authentication.getPrincipal() instanceof UsersVOWrapper) {
            UsersVOWrapper userWrapper = (UsersVOWrapper) authentication.getPrincipal();
            UsersVO realUser = userWrapper.getRealUser();
            log.info(String.format("Real User: %s", realUser));
            
            if("admin001".equals(realUser.getUserId())) {
            	lvn = "/stackUp/admin";
            } else if(!StringUtils.isEmpty(realUser.getMemberVO().getMemNm())) {
            	lvn = "/stackUp";
            } else if(!StringUtils.isEmpty(realUser.getCompanyVO().getCompNm())) {
            	lvn = "/stackUp/company ";
            } else {
            	lvn = "/stackUp/login";
            }
        }
        boolean rememberMe = "on".equals(request.getParameter("rememberMeCheckbox"));
        if (rememberMe) {
            Cookie cookie = new Cookie("userId", username);
            cookie.setMaxAge(60 * 60 * 24 * 30); 
            cookie.setPath("/"); 
            response.addCookie(cookie); 
        } else {
            Cookie cookie = new Cookie("userId", null);
            cookie.setMaxAge(0);  
            cookie.setPath("/");  
            response.addCookie(cookie);  
        }
        log.info(String.format("lvn : %s", lvn));
        response.sendRedirect(lvn);
	}
}
