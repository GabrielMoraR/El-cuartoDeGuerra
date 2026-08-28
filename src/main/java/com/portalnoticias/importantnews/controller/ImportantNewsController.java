package com.portalnoticias.importantnews.controller;

import java.util.List;

import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize; // Importación obligatoria para seguridad basada en métodos
import org.springframework.web.bind.annotation.*;

import com.portalnoticias.importantnews.dto.*;
import com.portalnoticias.importantnews.service.ImportantNewsService;
import com.portalnoticias.shared.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/important-news")
@RequiredArgsConstructor
public class ImportantNewsController {

    private final ImportantNewsService service;

    // =========================================================
    // ADMIN (Protegido - Listado para la gestión del panel)
    // =========================================================
    @GetMapping
    @PreAuthorize("hasAuthority('IMPORTANT_VIEW')") // Restringido al panel administrativo para listar todas
    public ResponseEntity<ApiResponse<List<ImportantNewsResponse>>> findAll() {
        return ResponseEntity.ok(
            ApiResponse.<List<ImportantNewsResponse>>builder()
                .success(true)
                .message("Noticias importantes obtenidas.")
                .data(service.findAll())
                .build()
        );
    }

    // =========================================================
    // PUBLICO (Abierto - Usado por los lectores en el Frontend)
    // =========================================================
    @GetMapping("/active")
    // Se mantiene libre de @PreAuthorize para que cualquier visitante lea las alertas sin token
    public ResponseEntity<ApiResponse<List<ImportantNewsResponse>>> active() {
        return ResponseEntity.ok(
            ApiResponse.<List<ImportantNewsResponse>>builder()
                .success(true)
                .message("Noticias activas.")
                .data(service.findActive())
                .build()
        );
    }

    // =========================================================
    // POR ID (Protegido)
    // =========================================================
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('IMPORTANT_VIEW')") // Protegido para la consulta de auditoría individual
    public ResponseEntity<ApiResponse<ImportantNewsResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
            ApiResponse.<ImportantNewsResponse>builder()
                .success(true)
                .message("Noticia encontrada.")
                .data(service.findById(id))
                .build()
        );
    }
    // =========================================================
    // CREAR (Protegido)
    // =========================================================
    @PostMapping
    @PreAuthorize("hasAuthority('IMPORTANT_CREATE')") // Solo Administradores autorizados pueden lanzar alertas urgentes
    public ResponseEntity<ApiResponse<ImportantNewsResponse>> save(@Valid @RequestBody ImportantNewsRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(
                ApiResponse.<ImportantNewsResponse>builder()
                    .success(true)
                    .message("Noticia creada.")
                    .data(service.save(request))
                    .build()
            );
    }

    // =========================================================
    // ACTUALIZAR (Protegido)
    // =========================================================
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('IMPORTANT_EDIT')") // Bloquea modificaciones maliciosas de información de última hora
    public ResponseEntity<ApiResponse<ImportantNewsResponse>> update(@PathVariable Long id, @RequestBody ImportantNewsRequest request) {
        return ResponseEntity.ok(
            ApiResponse.<ImportantNewsResponse>builder()
                .success(true)
                .message("Noticia actualizada.")
                .data(service.update(id, request))
                .build()
        );
    }

    // =========================================================
    // ELIMINAR (Protegido)
    // =========================================================
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('IMPORTANT_DELETE')") // Control total y restrictivo sobre la remoción de alertas
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(
            ApiResponse.<Void>builder()
                .success(true)
                .message("Noticia eliminada.")
                .build()
        );
    }
}
