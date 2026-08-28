package com.portalnoticias.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; 
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.portalnoticias.shared.pagination.PageResponse;
import com.portalnoticias.shared.response.ApiResponse;
import com.portalnoticias.user.dto.UserRequest;
import com.portalnoticias.user.dto.UserResponse;
import com.portalnoticias.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService service;

    // 🔒 CORREGIDO: hasRole asume automáticamente el prefijo 'ROLE_', evalúa si tienes 'ROLE_ADMINISTRADOR'
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        ApiResponse<PageResponse<UserResponse>> response =
                ApiResponse.<PageResponse<UserResponse>>builder()
                        .success(true)
                        .message("Usuarios obtenidos correctamente.")
                        .data(service.findAll(page, size, sortBy, direction))
                        .build();

        return ResponseEntity.ok(response);
    }

    // 🔒 CORREGIDO: Si manejas roles en español, usa los nombres exactos sin el prefijo manual
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'EDITOR')")
    public ResponseEntity<ApiResponse<UserResponse>> getById(
            @PathVariable Long id) {

        ApiResponse<UserResponse> response =
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("Usuario encontrado.")
                        .data(service.findById(id))
                        .build();

        return ResponseEntity.ok(response);
    }

    // 🔒 CORREGIDO
    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ApiResponse<UserResponse>> save(
            @Valid @RequestBody UserRequest request) {

        ApiResponse<UserResponse> response =
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("Usuario creado correctamente.")
                        .data(service.save(request))
                        .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // 🔒 CORREGIDO
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ApiResponse<UserResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request) {

        ApiResponse<UserResponse> response =
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("Usuario actualizado correctamente.")
                        .data(service.update(id, request))
                        .build();

        return ResponseEntity.ok(response);
    }

    // 🔒 CORREGIDO
    @PostMapping(
            value = "/{id}/photo",
            consumes = "multipart/form-data"
    )
    @PreAuthorize("hasAnyRole('ADMINISTRADOR', 'EDITOR')")
    public ResponseEntity<ApiResponse<UserResponse>> uploadPhoto(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        ApiResponse<UserResponse> response =
                ApiResponse.<UserResponse>builder()
                        .success(true)
                        .message("Foto de perfil actualizada correctamente.")
                        .data(service.uploadPhoto(id, file))
                        .build();

        return ResponseEntity.ok(response);
    }

    // 🔒 CORREGIDO
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        service.delete(id);

        ApiResponse<Void> response =
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Usuario eliminado correctamente.")
                        .build();

        return ResponseEntity.ok(response);
    }
}
