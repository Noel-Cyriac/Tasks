package com.nc.Task2.repository;

import com.nc.Task2.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, Long> {
    Optional<InvalidatedToken> findByToken(String token);
}