package com.portalnoticias.columnist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.portalnoticias.columnist.dto.ColumnistRequest;
import com.portalnoticias.columnist.dto.ColumnistResponse;
import com.portalnoticias.columnist.service.ColumnistService;
import com.portalnoticias.shared.pagination.PageResponse;
import com.portalnoticias.shared.response.ApiResponse;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/columnists")
@RequiredArgsConstructor
@Validated
public class ColumnistController {

        private final ColumnistService service;

        @GetMapping
        public ResponseEntity<ApiResponse<PageResponse<ColumnistResponse>>> getAll(

                        @RequestParam(defaultValue = "0") int page,

                        @RequestParam(defaultValue = "10") int size,

                        @RequestParam(defaultValue = "id") String sortBy,

                        @RequestParam(defaultValue = "asc") String direction

        ) {

                ApiResponse<PageResponse<ColumnistResponse>> response = ApiResponse
                                .<PageResponse<ColumnistResponse>>builder()
                                .success(true)
                                .message("Columnistas obtenidos correctamente.")
                                .data(service.findAll(page, size, sortBy, direction))
                                .build();

                return ResponseEntity.ok(response);

        }

        @GetMapping("/{id}")
        public ResponseEntity<ApiResponse<ColumnistResponse>> getById(
                        @PathVariable Long id) {

                ApiResponse<ColumnistResponse> response = ApiResponse.<ColumnistResponse>builder()
                                .success(true)
                                .message("Columnista encontrado.")
                                .data(service.findById(id))
                                .build();

                return ResponseEntity.ok(response);

        }

        @PostMapping
        public ResponseEntity<ApiResponse<ColumnistResponse>> save(

                        @Valid @RequestBody ColumnistRequest request

        ) {

                ApiResponse<ColumnistResponse> response = ApiResponse.<ColumnistResponse>builder()
                                .success(true)
                                .message("Columnista creado correctamente.")
                                .data(service.save(request))
                                .build();

                return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(response);

        }

        @PutMapping("/{id}")
        public ResponseEntity<ApiResponse<ColumnistResponse>> update(

                        @PathVariable Long id,

                        @Valid @RequestBody ColumnistRequest request

        ) {

                ApiResponse<ColumnistResponse> response = ApiResponse.<ColumnistResponse>builder()
                                .success(true)
                                .message("Columnista actualizado correctamente.")
                                .data(service.update(id, request))
                                .build();

                return ResponseEntity.ok(response);

        }

        @DeleteMapping("/{id}")
        public ResponseEntity<ApiResponse<Void>> delete(
                        @PathVariable Long id) {

                service.delete(id);

                ApiResponse<Void> response = ApiResponse.<Void>builder()
                                .success(true)
                                .message("Columnista eliminado correctamente.")
                                .build();

                return ResponseEntity.ok(response);

        }

        @PostMapping("/{id}/photo")
        public ResponseEntity<ApiResponse<ColumnistResponse>> uploadPhoto(

                        @PathVariable Long id,

                        @RequestParam("file") MultipartFile file) {

                ApiResponse<ColumnistResponse> response = ApiResponse.<ColumnistResponse>builder()
                                .success(true)
                                .message("Imagen subida correctamente.")
                                .data(service.uploadPhoto(id, file))
                                .build();

                return ResponseEntity.ok(response);

        }

        @DeleteMapping("/{id}/photo")
        public ResponseEntity<ApiResponse<Void>> deletePhoto(
                        @PathVariable Long id) {

                service.deletePhoto(id);

                ApiResponse<Void> response = ApiResponse.<Void>builder()
                                .success(true)
                                .message("Imagen eliminada correctamente.")
                                .build();

                return ResponseEntity.ok(response);

        }
}