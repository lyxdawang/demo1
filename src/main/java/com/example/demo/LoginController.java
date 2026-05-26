package com.example.demo;

import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/api/login")
    public Map<String, Object> login(@RequestBody User user) {
        Map<String, Object> map = new HashMap<>();
        boolean ok = loginService.login(user.getUsername(), user.getPassword());

        if (ok) {
            map.put("code", 200);
            map.put("msg", "登录成功");
        } else {
            map.put("code", 401);
            map.put("msg", "用户名或密码错误");
        }
        return map;
    }
}