package com.portalnoticias.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.portalnoticias.auth.dto.LoginRequest;
import com.portalnoticias.auth.dto.LoginResponse;
import com.portalnoticias.auth.service.AuthService;
import com.portalnoticias.shared.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @Valid
            @RequestBody
            LoginRequest request
    ) {

        return ResponseEntity.ok(

                ApiResponse.<LoginResponse>builder()

                        .success(true)

                        .message(
                                "Inicio de sesión exitoso."
                        )

                        .data(
                                authService.login(request)
                        )

                        .build()

        );

    }

}