package com.portalnoticias.videonota.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Importación obligatoria para seguridad
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.portalnoticias.shared.response.ApiResponse;
import com.portalnoticias.videonota.dto.VideoNotaRequest;
import com.portalnoticias.videonota.dto.VideoNotaResponse;
import com.portalnoticias.videonota.service.VideoNotaService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/video-notas")
@RequiredArgsConstructor
@Validated
public class VideoNotaController {

    private final VideoNotaService service;


    // ============================================================
    // LISTAR VIDEOS (Público - Sin restricciones en SecurityConfig)
    // ============================================================

    @GetMapping
    public ResponseEntity<ApiResponse<List<VideoNotaResponse>>> getAll() {

        return ResponseEntity.ok(
                ApiResponse.<List<VideoNotaResponse>>builder()
                        .success(true)
                        .message("VideoNotas obtenidas correctamente.")
                        .data(service.findAll())
                        .build()
        );
    }


    // ============================================================
    // VIDEOS POR CATEGORÍA (Público)
    // ============================================================

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<ApiResponse<List<VideoNotaResponse>>> getByCategoria(

            @PathVariable
            @Positive(message = "El id debe ser mayor a cero.")
            Long categoriaId

    ) {

        return ResponseEntity.ok(
                ApiResponse.<List<VideoNotaResponse>>builder()
                        .success(true)
                        .message("VideoNotas obtenidas correctamente.")
                        .data(
                                service.findByCategoria(categoriaId)
                        )
                        .build()
        );
    }


    // ============================================================
    // BUSCAR POR ID (Público)
    // ============================================================

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VideoNotaResponse>> getById(

            @PathVariable
            @Positive(message = "El id debe ser mayor a cero.")
            Long id

    ) {

        return ResponseEntity.ok(
                ApiResponse.<VideoNotaResponse>builder()
                        .success(true)
                        .message("VideoNota encontrada.")
                        .data(service.findById(id))
                        .build()
        );
    }


    // ============================================================
    // CREAR VIDEO (Protegido - Requiere Token y Permiso Estricto)
    // ============================================================

    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("hasAuthority('VIDEOS_CREATE')") // Valida el código asignado al Administrador
    public ResponseEntity<ApiResponse<VideoNotaResponse>> save(

            @RequestPart("data")
            @Valid
            VideoNotaRequest request,

            @RequestPart("video")
            MultipartFile video

    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.<VideoNotaResponse>builder()
                                .success(true)
                                .message(
                                        "VideoNota creada correctamente."
                                )
                                .data(
                                        service.save(
                                                request,
                                                video
                                        )
                                )
                                .build()
                );
    }
    // ============================================================
    // ACTUALIZAR DATOS (Protegido)
    // ============================================================

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('VIDEOS_EDIT')") // Solo usuarios con este permiso pueden alterar el texto
    public ResponseEntity<ApiResponse<VideoNotaResponse>> update(

            @PathVariable
            @Positive(message = "El id debe ser mayor a cero.")
            Long id,

            @Valid
            @RequestBody
            VideoNotaRequest request

    ) {

        return ResponseEntity.ok(
                ApiResponse.<VideoNotaResponse>builder()
                        .success(true)
                        .message(
                                "VideoNota actualizada correctamente."
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
    // CAMBIAR VIDEO (Protegido)
    // ============================================================

    @PutMapping(
            value = "/{id}/video",
            consumes = "multipart/form-data"
    )
    @PreAuthorize("hasAuthority('VIDEOS_EDIT')") // Reutiliza el permiso de edición para archivos multimedia
    public ResponseEntity<ApiResponse<VideoNotaResponse>> updateVideo(

            @PathVariable
            @Positive(message = "El id debe ser mayor a cero.")
            Long id,

            @RequestPart("video")
            MultipartFile video

    ) {

        return ResponseEntity.ok(
                ApiResponse.<VideoNotaResponse>builder()
                        .success(true)
                        .message(
                                "Video actualizado correctamente."
                        )
                        .data(
                                service.updateVideo(
                                        id,
                                        video
                                )
                        )
                        .build()
        );
    }


    // ============================================================
    // REGISTRAR VISTA (Público - Validado por la regla de SecurityConfig)
    // ============================================================

    @PostMapping("/{id}/view")
    public ResponseEntity<ApiResponse<Void>> incrementViews(

            @PathVariable
            @Positive(message = "El id debe ser mayor a cero.")
            Long id

    ) {

        service.incrementViews(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message(
                                "Vista registrada correctamente."
                        )
                        .data(null)
                        .build()
        );
    }


    // ============================================================
    // ELIMINAR (Protegido)
    // ============================================================

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('VIDEOS_DELETE')") // Destrucción física en DB y Cloudinary restringida
    public ResponseEntity<ApiResponse<Void>> delete(

            @PathVariable
            @Positive(message = "El id debe ser mayor a cero.")
            Long id

    ) {

        service.delete(id);

        return ResponseEntity.ok(
                ApiResponse.<Void>builder()
                        .success(true)
                        .message(
                                "VideoNota eliminada correctamente."
                        )
                        .data(null)
                        .build()
        );
    }
}
