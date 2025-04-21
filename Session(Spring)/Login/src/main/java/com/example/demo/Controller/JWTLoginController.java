package com.example.demo.Controller;

import com.example.demo.DTO.User;
import com.example.demo.Util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jwt-auth")
public class JWTLoginController {

    private final JwtUtil jwtUtil;

    public JWTLoginController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user, HttpServletResponse response) {
        // 예시: 아이디 비번이 정확하다고 가정
        if ("test".equals(user.getUsername()) && "1234".equals(user.getPassword())) {
            String token = jwtUtil.generateToken(user.getUsername());

            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(86400); // 1일

            response.addCookie(cookie);
            return ResponseEntity.ok("로그인 성공");
        }
        return ResponseEntity.status(401).body("로그인 실패");
    }

    @GetMapping("/me")
    public ResponseEntity<String> me(@CookieValue(name = "token", required = false) String token) {
        if (token != null && jwtUtil.isValid(token)) {
            String username = jwtUtil.validateTokenAndGetUsername(token);
            return ResponseEntity.ok("현재 사용자: " + username);
        }
        return ResponseEntity.status(401).body("로그인 필요");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok("로그아웃 완료");
    }
}
