package com.portalnoticias.directory.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Importación obligatoria para seguridad basada en métodos
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.portalnoticias.directory.dto.DirectoryRequest;
import com.portalnoticias.directory.dto.DirectoryResponse;
import com.portalnoticias.directory.service.DirectoryService;
import com.portalnoticias.shared.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/directory")
@RequiredArgsConstructor
@Validated
public class DirectoryController {


    private final DirectoryService service;


    // =========================================================
    // TODOS (Protegido - Usado para la gestión del Admin)
    // =========================================================

    @GetMapping
    @PreAuthorize("hasAuthority('DIRECTORY_VIEW')") // Restringido al panel administrativo para listar todos
    public ResponseEntity<
            ApiResponse<List<DirectoryResponse>>
    > getAll() {

        return ResponseEntity.ok(
            ApiResponse.<List<DirectoryResponse>>builder()
                .success(true)
                .message("Directorio obtenido correctamente.")
                .data(service.findAll())
                .build()
        );
    }


    // =========================================================
    // ACTIVOS (Público - Usado por los lectores en el Frontend)
    // =========================================================

    @GetMapping("/active")
    // Se mantiene libre de @PreAuthorize para que cualquier visitante lea el directorio sin token
    public ResponseEntity<
            ApiResponse<List<DirectoryResponse>>
    > getActive() {

        return ResponseEntity.ok(
            ApiResponse.<List<DirectoryResponse>>builder()
                .success(true)
                .message("Directorio activo obtenido correctamente.")
                .data(service.findActive())
                .build()
        );
    }


    // =========================================================
    // POR ID (Protegido)
    // =========================================================

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('DIRECTORY_VIEW')") // Protegido para la consulta de auditoría individual
    public ResponseEntity<
            ApiResponse<DirectoryResponse>
    > getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
            ApiResponse.<DirectoryResponse>builder()
                .success(true)
                .message("Registro encontrado.")
                .data(service.findById(id))
                .build()
        );
    }
    // =========================================================
    // CREAR (Protegido)
    // =========================================================

    @PostMapping
    @PreAuthorize("hasAuthority('DIRECTORY_CREATE')") // Solo Administradores autorizados pueden insertar nuevos comercios
    public ResponseEntity<
            ApiResponse<DirectoryResponse>
    > save(
            @Valid
            @RequestBody DirectoryRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                    ApiResponse.<DirectoryResponse>builder()
                        .success(true)
                        .message("Registro creado correctamente.")
                        .data(service.save(request))
                        .build()
                );
    }


    // =========================================================
    // ACTUALIZAR (Protegido)
    // =========================================================

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('DIRECTORY_EDIT')") // Bloquea modificaciones maliciosas de información comercial
    public ResponseEntity<
            ApiResponse<DirectoryResponse>
    > update(
            @PathVariable Long id,
            @Valid
            @RequestBody DirectoryRequest request) {

        return ResponseEntity.ok(
            ApiResponse.<DirectoryResponse>builder()
                .success(true)
                .message("Registro actualizado correctamente.")
                .data(service.update(id, request))
                .build()
        );
    }


    // =========================================================
    // SUBIR FOTO (Protegido)
    // =========================================================

    @PostMapping(
        value = "/{id}/photo",
        consumes = "multipart/form-data"
    )
    @PreAuthorize("hasAuthority('DIRECTORY_EDIT')") // Reutiliza el permiso de edición para la carga multimedia
    public ResponseEntity<
            ApiResponse<DirectoryResponse>
    > uploadPhoto(
            @PathVariable Long id,
            @RequestParam("file")
            MultipartFile file
    ) {

        return ResponseEntity.ok(
            ApiResponse.<DirectoryResponse>builder()
                .success(true)
                .message("Foto actualizada correctamente.")
                .data(service.uploadPhoto(id, file))
                .build()
        );
    }


    // =========================================================
    // ELIMINAR (Protegido)
    // =========================================================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DIRECTORY_DELETE')") // Control total y restrictivo sobre la remoción de datos
    public ResponseEntity<
            ApiResponse<Void>
    > delete(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.ok(
            ApiResponse.<Void>builder()
                .success(true)
                .message("Registro eliminado correctamente.")
                .build()
        );
    }
}
