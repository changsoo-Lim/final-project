package kr.or.ddit.notification.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import kr.or.ddit.notification.dao.NotificationMapper;
import kr.or.ddit.vo.NotificationVO;
import kr.or.ddit.vo.StatusVO;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    @Inject
    private NotificationMapper notificationMapper;

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    // Emitter 등록 (타임아웃 설정 포함)
    public void registerEmitter(String memId, SseEmitter emitter) {
        emitters.put(memId, emitter);

        // 연결 종료 시 emitter 제거
        emitter.onCompletion(() -> emitters.remove(memId));
        emitter.onTimeout(() -> emitters.remove(memId));
        emitter.onError((e) -> emitters.remove(memId));

        try {
            // 연결 시 기본 메시지와 재연결 주기 설정
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("Connected")
                    .reconnectTime(5000)); // 재연결 주기 5초
        } catch (IOException e) {
            emitters.remove(memId);
        }
    }

    // Emitter 제거
    public void removeEmitter(String memId) {
        emitters.remove(memId);
    }

    // 실시간 알림 전송
    @Override
    public void sendNotification(String memId, String message, String url) {
        SseEmitter emitter = emitters.get(memId);
        if (emitter != null) {
            try {
                String jsonData = String.format("{\"content\":\"%s\", \"url\":\"%s\", \"time\":\"%s\"}", 
                                                message, url, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                emitter.send(SseEmitter.event()
                        .name("notification")
                        .data(jsonData)
                        .reconnectTime(5000));
                log.info("Notification sent to {}: {}", memId, jsonData);
            } catch (IOException e) {
                emitters.remove(memId);
                log.error("Failed to send notification to {}: {}", memId, e.getMessage());
            }
        } else {
            log.warn("No emitter found for memId: {}", memId);
        }
    }



    // 알림 전송 및 DB 저장
    public void notifyAndSave(NotificationVO notificationVO) {
        // 1. 수신 여부 확인
        String status = notificationMapper.getNotificationStatus(notificationVO.getMemId(), notificationVO.getNotificType());
        log.info("================ {} ==================",status);
        if ("Y".equals(status)) {
        	// 2. 알림 메시지 생성
        	String content = createNotificationMessage(notificationVO.getNotificType(), notificationVO.getNotificCont());
        	
        	// 3. 실시간 알림 전송
        	sendNotification(notificationVO.getMemId(), content, notificationVO.getNotificUrl());
        	
        	// 4. 알림 데이터 저장
        	notificationMapper.insertNotification(notificationVO);
        }

    }

    // 알림 메시지 생성
    public String createNotificationMessage(String type, String extraInfo) {
        switch (type) {
            case "REC":
                return "[추천 공고] 새로운 공고가 등록되었습니다: " + extraInfo;
            case "POSITION":
                return "[포지션 제안] 새로운 포지션 제안이 도착했습니다.";
            case "COMMENT":
                return "[댓글 알림] 게시글에 새로운 댓글이 달렸습니다.";
            case "RESUME":
                return "[이력서 열람] 귀하의 이력서를 열람한 회사가 있습니다.";
            default:
                return "알 수 없는 유형의 알림입니다.";
        }
    }

	@Override
	public List<StatusVO> readStatusList(String memId) {
		return notificationMapper.selectStatusList(memId);
	}
}
