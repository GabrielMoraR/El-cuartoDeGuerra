package com.portalnoticias.category.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.portalnoticias.category.dto.CategoryRequest;
import com.portalnoticias.category.dto.CategoryResponse;
import com.portalnoticias.category.service.CategoryService;
import com.portalnoticias.shared.pagination.PageResponse;
import com.portalnoticias.shared.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Validated
public class CategoryController {

    private final CategoryService service;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<CategoryResponse>>> getAll(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction

    ) {

        ApiResponse<PageResponse<CategoryResponse>> response =
                ApiResponse.<PageResponse<CategoryResponse>>builder()
                        .success(true)
                        .message("Categorías obtenidas correctamente.")
                        .data(service.findAll(page, size, sortBy, direction))
                        .build();

        return ResponseEntity.ok(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getById(
            @PathVariable Long id) {

        ApiResponse<CategoryResponse> response =
                ApiResponse.<CategoryResponse>builder()
                        .success(true)
                        .message("Categoría encontrada.")
                        .data(service.findById(id))
                        .build();

        return ResponseEntity.ok(response);

    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> save(
            @Valid @RequestBody CategoryRequest request) {

        ApiResponse<CategoryResponse> response =
                ApiResponse.<CategoryResponse>builder()
                        .success(true)
                        .message("Categoría creada correctamente.")
                        .data(service.save(request))
                        .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request) {

        ApiResponse<CategoryResponse> response =
                ApiResponse.<CategoryResponse>builder()
                        .success(true)
                        .message("Categoría actualizada correctamente.")
                        .data(service.update(id, request))
                        .build();

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id) {

        service.delete(id);

        ApiResponse<Void> response =
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Categoría eliminada correctamente.")
                        .build();

        return ResponseEntity.ok(response);

    }

}