package kr.or.ddit.notification.service;

import java.util.List;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import kr.or.ddit.vo.NotificationVO;
import kr.or.ddit.vo.StatusVO;

public interface NotificationService {
	public void registerEmitter(String memId, SseEmitter emitter);

    public void removeEmitter(String memId);

    public void sendNotification(String memId, String message, String url);
    
    public void notifyAndSave(NotificationVO notificationVO);
    
    public List<StatusVO> readStatusList(String memId);
}
