package com.portalnoticias.column.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.portalnoticias.column.dto.ColumnResponse;
import com.portalnoticias.column.service.ColumnService;
import com.portalnoticias.shared.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/public/columns")
@RequiredArgsConstructor
public class PublicColumnController {

    private final ColumnService service;

    @GetMapping("/latest")
    public ResponseEntity<ApiResponse<List<ColumnResponse>>> latest() {
        return ResponseEntity.ok(
                ApiResponse.<List<ColumnResponse>>builder()
                        .success(true)
                        .message("Columnas obtenidas correctamente.")
                        .data(service.getLatestColumns())
                        .build()
        );
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<ColumnResponse>> getBySlug(
            @PathVariable String slug
    ) {
        return ResponseEntity.ok(
                ApiResponse.<ColumnResponse>builder()
                        .success(true)
                        .message("Columna obtenida correctamente.")
                        .data(service.findBySlug(slug))
                        .build()
        );
    }

    // 🌟 AGREGA ESTE MÉTODO AQUÍ PARA HACERLO PÚBLICO
    @PatchMapping("/{id}/view")
    public ResponseEntity<ApiResponse<Void>> increaseColumnViews(
            @PathVariable Long id
    ) {
        // Usamos el método exacto que ya tienes listo en tu servicio
        service.increaseViews(id); 

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Vistas aumentadas correctamente desde endpoint público.")
                        .build()
        );
    }
}
