package com.nc.Task2.service;

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
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final InvalidatedTokenRepository invalidatedTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration;

    // ---------------- Register ----------------
    public String register(RegisterRequest req) {

        if (!req.getPassword().equals(req.getConfirmPassword()))
            throw new RuntimeException("Passwords do not match");

        if (userRepository.findByUsername(req.getUsername()).isPresent())
            throw new RuntimeException("Username already taken");

        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        userRepository.save(user);

        return "User registered successfully";
    }

    // ---------------- Login ----------------
    @Transactional
    public Map<String, String> login(Map<String, String> req) {
        User user = userRepository.findByUsername(req.get("username"))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(req.get("password"), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // Delete old token and immediately flush to DB
        refreshTokenRepository.deleteByUser(user);
        refreshTokenRepository.flush();

        // Save new token
        RefreshToken tokenEntity = new RefreshToken();
        tokenEntity.setUser(user);
        tokenEntity.setToken(refreshToken);
        tokenEntity.setExpiryDate(Instant.now().plusMillis(refreshExpiration));

        refreshTokenRepository.save(tokenEntity);

        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }

    // ---------------- Refresh ----------------
    public Map<String, String> refresh(String refreshTokenValue) {

        RefreshToken token = refreshTokenRepository.findByToken(refreshTokenValue)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (token.getExpiryDate().isBefore(Instant.now()))
            throw new RuntimeException("Refresh token expired");

        String accessToken = jwtService.generateAccessToken(token.getUser());

        return Map.of("accessToken", accessToken);
    }

    // ---------------- Logout ----------------
    @Transactional
    public String logout(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new RuntimeException("Missing or invalid Authorization header");

        String accessToken = authHeader.substring(7);
        String username = jwtService.extractUsername(accessToken);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        refreshTokenRepository.deleteByUser(user);

        Instant expiry = jwtService.extractExpiration(accessToken).toInstant();

        InvalidatedToken invalidated = new InvalidatedToken();
        invalidated.setToken(accessToken);
        invalidated.setExpiryDate(expiry);

        invalidatedTokenRepository.save(invalidated);

        return "Logged out successfully";
    }
}