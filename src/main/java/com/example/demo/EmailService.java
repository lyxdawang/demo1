package com.example.demo;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public String generateCode() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    public void sendEmail(String to, String code) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("2553205634@qq.com");
        helper.setTo(to);
        helper.setSubject("登录验证码");
        helper.setText("您的验证码是：" + code + "，5分钟内有效");

        mailSender.send(message);
    }
}