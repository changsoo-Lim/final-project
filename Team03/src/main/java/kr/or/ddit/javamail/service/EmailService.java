package kr.or.ddit.javamail.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // 이메일 보내는 메서드
    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        // 발신자, 수신자, 제목, 내용 설정
        helper.setFrom("stackup406@naver.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);

        try {
            mailSender.send(mimeMessage);
            System.out.println("이메일 전송 완료!");
        } catch (MailException e) {
            e.printStackTrace();
            throw new MessagingException("이메일 전송에 실패했습니다.");
        }
    }
    
    // 다중 이메일 발송
    public void sendEmails(List<String> recipients, String subject, String body) throws MessagingException {
        for (String email : recipients) {
            sendEmail(email, subject, body);
        }
    }
}
