package kr.or.ddit.javamail.controller;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.ddit.javamail.service.EmailService;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    // 이메일 전송 예시
    @PostMapping("/send/{email}")
    public Map<String, Object> sendEmail(@PathVariable String email) {
    	Map<String, Object> resultMap = new HashMap<>();
        String subject = "StackUp 회원 인증 코드";
        String code = generateCode();
        String body = "귀하의 인증 코드입니다 : "+code;  // 6자리 인증 코드 예시
        String message = null;
        try {
            emailService.sendEmail(email, subject, body);
            message = "이메일이 성공적으로 전송되었습니다."; 
            resultMap.put(message, body);
            resultMap.put("code", code);
        } catch (MessagingException e) {
        	message = "이메일 전송에 실패했습니다: " + e.getMessage();
            resultMap.put(message, body);
        }
        return resultMap;
    }
    
    private String generateCode() {
        return String.format("%06d", (int)(Math.random() * 1000000)); // 6자리 랜덤 인증번호
    }
}
