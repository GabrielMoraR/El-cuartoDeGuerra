package com.portalnoticias.videonota.dto;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoNotaResponse {

    private Long id;

    private String titulo;

    private String descripcion;

    private String videoUrl;

    private String thumbnailUrl;

    private Long vistas;

    private Long categoriaId;

    private String categoriaNombre;

    private String categoriaSlug;

    private String categoriaColor;

    private Boolean activo;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}