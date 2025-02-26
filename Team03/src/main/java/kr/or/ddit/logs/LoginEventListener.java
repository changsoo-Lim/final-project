package kr.or.ddit.logs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class LoginEventListener {
	
	// 로그인 로그 저장소 (메모리 방식)
    private final List<String> loginLogs = new ArrayList<>();

    @EventListener
    public void handleAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        String username = authentication.getName();
        String logEntry = "User " + username + " logged in at " + java.time.LocalDateTime.now();
        
        // 로그 저장
        loginLogs.add(logEntry);
        System.out.println(logEntry);  // 로그 출력 (디버깅용)
    }

    // 로그 조회 메서드
    public List<String> getLoginLogs() {
        return loginLogs;
    }
}
