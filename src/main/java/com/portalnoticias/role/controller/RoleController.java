package com.portalnoticias.role.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.portalnoticias.role.dto.PermissionRequest;
import com.portalnoticias.role.dto.RoleRequest;
import com.portalnoticias.role.dto.RoleResponse;
import com.portalnoticias.role.service.RoleService;
import com.portalnoticias.shared.response.ApiResponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Validated
public class RoleController {

    private final RoleService service;


    // ============================================================
    // LISTAR ROLES
    // ============================================================

    @GetMapping
    @PreAuthorize("hasAuthority('ROLES_VIEW')")
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.<List<RoleResponse>>builder()
                        .success(true)
                        .message(
                                "Roles obtenidos correctamente."
                        )
                        .data(service.findAll())
                        .build()
        );

    }


    // ============================================================
    // BUSCAR ROL
    // ============================================================

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLES_VIEW')")
    public ResponseEntity<ApiResponse<RoleResponse>> getById(

            @PathVariable
            @Positive(
                    message = "El id debe ser mayor a cero."
            )
            Long id

    ) {

        return ResponseEntity.ok(
                ApiResponse.<RoleResponse>builder()
                        .success(true)
                        .message("Rol encontrado.")
                        .data(service.findById(id))
                        .build()
        );

    }


    // ============================================================
    // CREAR ROL
    // ============================================================

    @PostMapping
    @PreAuthorize("hasAuthority('ROLES_CREATE')")
    public ResponseEntity<ApiResponse<RoleResponse>> save(

            @Valid
            @RequestBody
            RoleRequest request

    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<RoleResponse>builder()
                                .success(true)
                                .message(
                                        "Rol creado correctamente."
                                )
                                .data(
                                        service.save(request)
                                )
                                .build()
                );

    }


    // ============================================================
    // ACTUALIZAR ROL
    // ============================================================

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLES_EDIT')")
    public ResponseEntity<ApiResponse<RoleResponse>> update(

            @PathVariable
            @Positive(
                    message = "El id debe ser mayor a cero."
            )
            Long id,

            @Valid
            @RequestBody
            RoleRequest request

    ) {

        return ResponseEntity.ok(
                ApiResponse.<RoleResponse>builder()
                        .success(true)
                        .message(
                                "Rol actualizado correctamente."
                        )
                        .data(
                                service.update(
                                        id,
                                        request
                                )
                        )
                        .build()
        );

    }


    // ============================================================
    // ASIGNAR PERMISOS
    // ============================================================

    @PutMapping("/{id}/permissions")
    @PreAuthorize("hasAuthority('ROLES_ASSIGN')")
    public ResponseEntity<ApiResponse<RoleResponse>> updatePermissions(

            @PathVariable
            @Positive(
                    message = "El id debe ser mayor a cero."
            )
            Long id,

            @Valid
            @RequestBody
            PermissionRequest request

    ) {

        return ResponseEntity.ok(
                ApiResponse.<RoleResponse>builder()
                        .success(true)
                        .message(
                                "Permisos del rol actualizados correctamente."
                        )
                        .data(
                                service.updatePermissions(
                                        id,
                                        request.getPermissionIds()
                                )
                        )
                        .build()
        );

    }


    // ============================================================
    // ELIMINAR ROL
    // ============================================================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLES_DELETE')")
    public ResponseEntity<ApiResponse<Void>> delete(

            @PathVariable
            @Positive(
                    message = "El id debe ser mayor a cero."
            )
            Long id

    ) {

        service.delete(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message(
                                "Rol eliminado correctamente."
                        )
                        .data(null)
                        .build()
        );

    }

}