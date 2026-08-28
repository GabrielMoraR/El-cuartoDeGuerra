package com.portalnoticias.auth.service.impl;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portalnoticias.auth.dto.LoginRequest;
import com.portalnoticias.auth.dto.LoginResponse;
import com.portalnoticias.auth.service.AuthService;
import com.portalnoticias.auth.service.JwtService;
import com.portalnoticias.role.entity.Permission;
import com.portalnoticias.user.entity.User;
import com.portalnoticias.user.repository.UserRepository;
import com.portalnoticias.shared.exception.ConflictException;
import com.portalnoticias.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest request) {

        String usernameOrEmail =
                request.getUsernameOrEmail()
                        .trim();

        User user =
                userRepository
                        .findByUsernameOrEmail(
                                usernameOrEmail,
                                usernameOrEmail
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Usuario o contraseña incorrectos."
                                )
                        );


        // =====================================================
        // USUARIO ACTIVO
        // =====================================================

        if (
            user.getActive() == null
            || !user.getActive()
        ) {

            throw new ConflictException(
                    "El usuario se encuentra inactivo."
            );

        }


        // =====================================================
        // CONTRASEÑA
        // =====================================================

        if (
            !passwordEncoder.matches(
                    request.getPassword(),
                    user.getPassword()
            )
        ) {

            throw new ResourceNotFoundException(
                    "Usuario o contraseña incorrectos."
            );

        }


        // =====================================================
        // GENERAR TOKEN
        // =====================================================

        String token =
                jwtService.generateToken(user);


        // =====================================================
        // ACTUALIZAR ÚLTIMO LOGIN
        // =====================================================

        user.setLastLogin(
                LocalDateTime.now()
        );

        userRepository.save(user);


        // =====================================================
        // PERMISOS
        // =====================================================

        Set<String> permissions =
                user.getRole()
                        .getPermissions()
                        .stream()
                        .map(Permission::getCode)
                        .collect(Collectors.toSet());


        // =====================================================
        // RESPUESTA
        // =====================================================

        return LoginResponse.builder()

                .token(token)

                .tokenType("Bearer")

                .userId(user.getId())

                .username(user.getUsername())

                .name(user.getNombre())

                .email(user.getEmail())

                .role(
                        user.getRole()
                                .getName()
                )

                .permissions(permissions)

                .build();
    }

}