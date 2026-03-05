package com.nc.Task2.controller;

import com.nc.Task2.dto.RegisterRequest;
import com.nc.Task2.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> req) {
        return authService.login(req);
    }

    @PostMapping("/refresh")
    public Map<String, String> refresh(@RequestBody Map<String, String> req) {
        return authService.refresh(req.get("refreshToken"));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        return ResponseEntity.ok(authService.logout(request));
    }
}