package kr.or.ddit.javamail.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // SMTP 서버 정보 설정
        mailSender.setHost("smtp.naver.com"); // 네이버 SMTP 서버
        mailSender.setPort(587); // 네이버 SMTP 포트 (TLS)
        mailSender.setUsername("stackup406@naver.com"); // 네이버 이메일
        mailSender.setPassword("stackup406!!"); // 네이버 이메일 비밀번호

        // 메일 서버 속성 설정
        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.starttls.required", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.naver.com");

        return mailSender;
    }
}
