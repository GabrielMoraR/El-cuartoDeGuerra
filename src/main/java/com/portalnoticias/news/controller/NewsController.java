package com.portalnoticias.news.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;
import java.util.List;

import com.portalnoticias.news.dto.NewsRequest;
import com.portalnoticias.news.dto.NewsResponse;
import com.portalnoticias.news.service.NewsService;
import com.portalnoticias.shared.pagination.PageResponse;
import com.portalnoticias.shared.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
@Validated
public class NewsController {

    private final NewsService service;

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<NewsResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        ApiResponse<PageResponse<NewsResponse>> response =
                ApiResponse.<PageResponse<NewsResponse>>builder()
                        .success(true)
                        .message("Noticias obtenidas correctamente.")
                        .data(service.findAll(page, size, sortBy, direction))
                        .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ApiResponse<NewsResponse>> getBySlug(
            @PathVariable String slug
    ) {
        ApiResponse<NewsResponse> response =
                ApiResponse.<NewsResponse>builder()
                        .success(true)
                        .message("Noticia encontrada.")
                        .data(service.findBySlug(slug))
                        .build();
        return ResponseEntity.ok(response);
    }
    
    @PostMapping
    @PreAuthorize("hasAuthority('NEWS_CREATE')")
    public ResponseEntity<ApiResponse<NewsResponse>> save(
            @Valid @RequestBody NewsRequest request
    ) {
        ApiResponse<NewsResponse> response =
                ApiResponse.<NewsResponse>builder()
                        .success(true)
                        .message("Noticia creada correctamente.")
                        .data(service.save(request))
                        .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('NEWS_EDIT')")
    public ResponseEntity<ApiResponse<NewsResponse>> update(
            @PathVariable Long id,
            @Valid @RequestBody NewsRequest request
    ) {
        ApiResponse<NewsResponse> response =
                ApiResponse.<NewsResponse>builder()
                        .success(true)
                        .message("Noticia actualizada correctamente.")
                        .data(service.update(id, request))
                        .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/views")
    public ResponseEntity<ApiResponse<Void>> increaseViews(
            @PathVariable Long id
    ) {
        service.increaseViews(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Vistas aumentadas correctamente.")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('NEWS_DELETE')")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id
    ) {
        service.delete(id);
        ApiResponse<Void> response =
                ApiResponse.<Void>builder()
                        .success(true)
                        .message("Noticia eliminada correctamente.")
                        .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/upload-image")
    @PreAuthorize("hasAuthority('NEWS_CREATE') or hasAuthority('NEWS_EDIT')")
    public ResponseEntity<ApiResponse<Map<String, String>>> uploadImage(
            @RequestParam("file") MultipartFile file
    ) {
        Map<String, String> response = service.uploadImage(file);
        return ResponseEntity.ok(
                ApiResponse.ok(
                    "Imagen subida correctamente.",
                    response
                )
        );
    }

    @GetMapping("/weekly-highlights")
    public ResponseEntity<ApiResponse<List<NewsResponse>>> getWeeklyHighlights() {
        ApiResponse<List<NewsResponse>> response =
                ApiResponse.<List<NewsResponse>>builder()
                        .success(true)
                        .message("Noticias destacadas obtenidas correctamente.")
                        .data(service.getWeeklyHighlights())
                        .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/latest")
    public ResponseEntity<ApiResponse<List<NewsResponse>>> getLatestNews() {
        ApiResponse<List<NewsResponse>> response = ApiResponse.<List<NewsResponse>>builder()
                .success(true)
                .message("Noticias más recientes obtenidas correctamente.")
                .data(service.getLatestNews())
                .build();
        return ResponseEntity.ok(response);
    }
   // Coloca esto dentro de tu PublicNewsController.java
// =========================================================
    // BUSCADOR (Corregido el nombre de la variable de servicio)
    // =========================================================
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<NewsResponse>>> searchNews(
            @RequestParam("query") String query
    ) {
        return ResponseEntity.ok(
                ApiResponse.<List<NewsResponse>>builder()
                        .success(true)
                        .message("Resultados de búsqueda obtenidos correctamente.")
                        .data(service.searchPublicNews(query)) // 🌟 CORREGIDO: Cambiado de 'newsService' a 'service'
                        .build()
        );
    }

}
