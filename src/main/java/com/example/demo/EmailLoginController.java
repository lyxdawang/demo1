package com.example.demo;

import org.springframework.web.bind.annotation.*;
import jakarta.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@CrossOrigin(origins = "*")
public class EmailLoginController {

    private final EmailService emailService;
    // 临时存验证码
    private final Map<String, String> codeCache = new ConcurrentHashMap<>();

    public EmailLoginController(EmailService emailService) {
        this.emailService = emailService;
    }

    // 1. 发送验证码到邮箱
    @PostMapping("/api/sendEmailCode")
    public Map<String, Object> sendCode(@RequestBody Map<String, String> map) throws MessagingException {
        String email = map.get("email");
        String code = emailService.generateCode();
        codeCache.put(email, code);
        emailService.sendEmail(email, code);

        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "验证码发送成功");
        return result;
    }

    // 2. 邮箱 + 验证码登录
    @PostMapping("/api/emailLogin")
    public Map<String, Object> emailLogin(@RequestBody Map<String, String> map) {
        String email = map.get("email");
        String code = map.get("code");
        String realCode = codeCache.get(email);

        Map<String, Object> result = new HashMap<>();

        if (realCode != null && realCode.equals(code)) {
            result.put("code", 200);
            result.put("msg", "QQ邮箱登录成功");
            codeCache.remove(email);
        } else {
            result.put("code", 401);
            result.put("msg", "验证码错误");
        }
        return result;
    }
}