package com.visionweb.app_vision_web.application.services;

import com.visionweb.app_vision_web.application.dto.LoginDto;
import com.visionweb.app_vision_web.application.dto.TokenDto;
import com.visionweb.app_vision_web.domain.contracts.service.GeradorTokenJwtService;
import com.visionweb.app_vision_web.domain.core.entities.jwt.JwtSettings;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class GeradorTokenJwtServiceImpl implements GeradorTokenJwtService {

    private final JwtSettings jwtSettings;

    public GeradorTokenJwtServiceImpl(JwtSettings jwtSettings) {
        this.jwtSettings = jwtSettings;
    }

    @Override
    public TokenDto gerarToken(LoginDto loginDto) throws Exception {
        Key key = Keys.hmacShaKeyFor(jwtSettings.getSecretKey().getBytes());

        Instant expiracao = Instant.now().plus(jwtSettings.getExpiryHours(), ChronoUnit.HOURS);

        String token = Jwts.builder()
                .setSubject(loginDto.getEmail()) // sub
                .claim("email", loginDto.getEmail()) // email
                .setId(UUID.randomUUID().toString()) // jti
                .setIssuer(jwtSettings.getIssuer())
                .setAudience(jwtSettings.getAudience())
                .setExpiration(Date.from(expiracao))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        TokenDto tokenDto = new TokenDto();
        tokenDto.setToken(token);
        tokenDto.setExpirationTime(Date.from(expiracao));

        return tokenDto;
    }
}
