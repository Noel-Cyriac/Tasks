package com.nc.Task2.controller;

import com.nc.Task2.dto.RegisterRequest;
import com.nc.Task2.entity.InvalidatedToken;
import com.nc.Task2.entity.RefreshToken;
import com.nc.Task2.entity.User;
import com.nc.Task2.jwt.JwtService;
import com.nc.Task2.repository.InvalidatedTokenRepository;
import com.nc.Task2.repository.RefreshTokenRepository;
import com.nc.Task2.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final InvalidatedTokenRepository invalidatedTokenRepository;

    // ---------------- Register ----------------
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest req) {
        if (!req.getPassword().equals(req.getConfirmPassword()))
            return ResponseEntity.badRequest().body("Passwords do not match");

        if (userRepository.findByUsername(req.getUsername()).isPresent())
            return ResponseEntity.badRequest().body("Username already taken");

        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    // ---------------- Login ----------------
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> req) {
        User user = userRepository.findByUsername(req.get("username"))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(req.get("password"), user.getPassword()))
            throw new RuntimeException("Invalid credentials");

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        RefreshToken tokenEntity = new RefreshToken();
        tokenEntity.setToken(refreshToken);
        tokenEntity.setUser(user);
        tokenEntity.setExpiryDate(Instant.now().plusMillis(604800000)); // 7 days
        refreshTokenRepository.save(tokenEntity);

        return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
    }

    // ---------------- Refresh ----------------
    @PostMapping("/refresh")
    public Map<String, String> refresh(@RequestBody Map<String, String> req) {
        RefreshToken token = refreshTokenRepository.findByToken(req.get("refreshToken"))
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (token.getExpiryDate().isBefore(Instant.now()))
            throw new RuntimeException("Refresh token expired");

        String accessToken = jwtService.generateAccessToken(token.getUser());
        return Map.of("accessToken", accessToken);
    }

    // ---------------- Logout ----------------
    @PostMapping("/logout")
    @Transactional
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            return ResponseEntity.badRequest().body("Missing or invalid Authorization header");

        String accessToken = authHeader.substring(7);
        String username = jwtService.extractUsername(accessToken);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Delete refresh tokens
        refreshTokenRepository.deleteByUser(user);

        // Save access token in invalidated tokens
        Instant expiry = jwtService.extractExpiration(accessToken).toInstant();

        InvalidatedToken invalidated = new InvalidatedToken();
        invalidated.setToken(accessToken);
        invalidated.setExpiryDate(expiry);
        invalidatedTokenRepository.save(invalidated);

        return ResponseEntity.ok("Logged out successfully");
    }
}