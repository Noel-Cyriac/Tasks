package com.nc.Task2.jwt;

import com.nc.Task2.repository.InvalidatedTokenRepository;
import com.nc.Task2.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final InvalidatedTokenRepository invalidatedTokenRepository;

    // Skip JWT validation for public endpoints
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        // Skip filter for login, register, refresh
        return path.startsWith("/api/auth/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                // Check if token is invalidated
                if (invalidatedTokenRepository.findByToken(token).isPresent()) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token invalidated");
                    return;
                }

                // Extract username from token
                String username = jwtService.extractUsername(token);

                // Set authentication in Spring Security context
                userRepository.findByUsername(username).ifPresent(user -> {
                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(user, null, List.of());
                    SecurityContextHolder.getContext().setAuthentication(auth);
                });

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token invalid or expired");
                return;
            }
        } else {
            // Authorization header missing on protected endpoints
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Access token missing");
            return;
        }

        chain.doFilter(request, response);
    }
}