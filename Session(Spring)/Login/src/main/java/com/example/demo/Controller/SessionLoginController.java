package com.example.demo.Controller;

import com.example.demo.DTO.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/session-auth")
public class SessionLoginController {
    private final Map<String, User> userStore = new HashMap<>();

    public SessionLoginController() {
        userStore.put("Lim", new User("lim","1234"));
        userStore.put("Cha", new User("cha","1234"));
    }

    @PostMapping("/login")
    public String login(@RequestBody User loginRequest, HttpSession session) {
        User user = userStore.get(loginRequest.getUsername());

        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            session.setAttribute("user", user);
            return "로그인 성공";
        } else {
            return "아이디 또는 비밀번호 오류";
        }
    }

    @GetMapping("/me")
    public Object getSessionUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "로그인 필요";
        }
        return user;
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "로그아웃 완료";
    }
}
