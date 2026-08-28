package com.portalnoticias.column.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import com.portalnoticias.column.dto.ColumnRequest;
import com.portalnoticias.column.dto.ColumnResponse;
import com.portalnoticias.column.service.ColumnService;
import com.portalnoticias.shared.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/columns")
@RequiredArgsConstructor
@Validated
public class ColumnController {

    private final ColumnService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ColumnResponse>>> getAll() {

        return ResponseEntity.ok(

                ApiResponse.<List<ColumnResponse>>builder()
                        .success(true)
                        .message("Columnas obtenidas correctamente.")
                        .data(service.findAll())
                        .build()

        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ColumnResponse>> getById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(

                ApiResponse.<ColumnResponse>builder()
                        .success(true)
                        .message("Columna encontrada.")
                        .data(service.findById(id))
                        .build()

        );

    }

    @PostMapping
    public ResponseEntity<ApiResponse<ColumnResponse>> save(
            @Valid @RequestBody ColumnRequest request
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)

                .body(

                        ApiResponse.<ColumnResponse>builder()
                                .success(true)
                                .message("Columna creada correctamente.")
                                .data(service.save(request))
                                .build()

                );

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ColumnResponse>> update(

            @PathVariable Long id,

            @Valid @RequestBody ColumnRequest request

    ) {

        return ResponseEntity.ok(

                ApiResponse.<ColumnResponse>builder()
                        .success(true)
                        .message("Columna actualizada correctamente.")
                        .data(service.update(id, request))
                        .build()

        );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id
    ) {

        service.delete(id);

        return ResponseEntity.ok(

                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Columna eliminada correctamente.")
                        .build()

        );

    }

    @PatchMapping("/{id}/view")
    public ResponseEntity<ApiResponse<Void>> increaseViews(
            @PathVariable Long id
    ) {

        service.increaseViews(id);

        return ResponseEntity.ok(

                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Vistas aumentadas correctamente.")
                        .build()

        );

    }
    @PostMapping("/upload-image")
public ResponseEntity<ApiResponse<Map<String,String>>> uploadImage(

        @RequestParam("file") MultipartFile file

){

    Map<String,String> response = service.uploadImage(file);

    return ResponseEntity.ok(

        ApiResponse.ok(

            "Imagen subida correctamente.",

            response

        )

    );

}

}