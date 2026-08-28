package com.portalnoticias.auth.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

// Importe correcto para HS256
import org.springframework.security.oauth2.jose.jws.MacAlgorithm; 
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.portalnoticias.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder jwtEncoder;

    public String generateToken(User user) {
        Instant now = Instant.now();

        String role = user.getRole().getName();

        var permissions = user.getRole()
                .getPermissions()
                .stream()
                .map(permission -> permission.getCode())
                .toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("portal-noticias-api")
                .issuedAt(now)
                .expiresAt(now.plus(8, ChronoUnit.HOURS))
                .subject(user.getUsername())
                .claim("userId", user.getId())
                .claim("username", user.getUsername())
                .claim("role", role)
                .claim("permissions", permissions)
                .build();

        // ✅ USAR MacAlgorithm EN LUGAR DE SignatureAlgorithm
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }
}   