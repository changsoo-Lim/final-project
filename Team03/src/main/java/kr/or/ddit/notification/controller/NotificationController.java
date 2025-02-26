package kr.or.ddit.notification.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import kr.or.ddit.notification.service.NotificationService;
import kr.or.ddit.vo.StatusVO;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
	
	@Inject
    private NotificationService notificationService;

	@GetMapping("/stream")
	public SseEmitter streamNotifications(Authentication authentication) {
	    if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
	        return new SseEmitter(0L);  // 비어 있는 Emitter 반환
	    }

	    String memId = ((UserDetails) authentication.getPrincipal()).getUsername();

	    // 타임아웃 60초 설정
	    SseEmitter emitter = new SseEmitter(60_000L);  // 60초 타임아웃

	    // Service에 emitter 등록
	    notificationService.registerEmitter(memId, emitter);

	    // 최초 연결 시 더미 데이터 전송 (연결 확인용)
	    try {
	        emitter.send(SseEmitter.event()
	                    .name("init")                // 이벤트 이름
	                    .data("connected"));         // 더미 데이터
	    } catch (IOException e) {
	        notificationService.removeEmitter(memId);
	        return emitter;
	    }

	    // 클라이언트 연결 종료 시 처리
	    emitter.onCompletion(() -> notificationService.removeEmitter(memId));
	    emitter.onTimeout(() -> notificationService.removeEmitter(memId));

	    return emitter;
	}

	@GetMapping("list")
	public List<StatusVO> list(Authentication authentication){
		
		String memId = ((UserDetails) authentication.getPrincipal()).getUsername();
		
		List<StatusVO> statusList = notificationService.readStatusList(memId);
		
		return statusList;
	}
}

