package com.portalnoticias.role.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.portalnoticias.role.dto.PermissionRequest;
import com.portalnoticias.role.dto.PermissionResponse;
import com.portalnoticias.role.service.PermissionService;
import com.portalnoticias.shared.response.ApiResponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
@Validated
public class PermissionController {

    private final PermissionService service;


    @GetMapping
    public ResponseEntity<ApiResponse<List<PermissionResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.<List<PermissionResponse>>builder()
                        .success(true)
                        .message("Permisos obtenidos correctamente.")
                        .data(service.findAll())
                        .build()
        );

    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionResponse>> getById(

            @PathVariable
            @Positive(message = "El id debe ser mayor a cero.")
            Long id

    ) {

        return ResponseEntity.ok(
                ApiResponse.<PermissionResponse>builder()
                        .success(true)
                        .message("Permiso encontrado.")
                        .data(service.findById(id))
                        .build()
        );

    }


    @PostMapping
    public ResponseEntity<ApiResponse<PermissionResponse>> save(

            @Valid
            @RequestBody
            PermissionRequest request

    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<PermissionResponse>builder()
                                .success(true)
                                .message("Permiso creado correctamente.")
                                .data(service.save(request))
                                .build()
                );

    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionResponse>> update(

            @PathVariable
            @Positive(message = "El id debe ser mayor a cero.")
            Long id,

            @Valid
            @RequestBody
            PermissionRequest request

    ) {

        return ResponseEntity.ok(
                ApiResponse.<PermissionResponse>builder()
                        .success(true)
                        .message("Permiso actualizado correctamente.")
                        .data(service.update(id, request))
                        .build()
        );

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(

            @PathVariable
            @Positive(message = "El id debe ser mayor a cero.")
            Long id

    ) {

        service.delete(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Permiso eliminado correctamente.")
                        .data(null)
                        .build()
        );

    }

}