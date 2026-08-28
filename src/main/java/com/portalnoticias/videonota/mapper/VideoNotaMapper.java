package com.portalnoticias.videonota.mapper;

import org.springframework.stereotype.Component;

import com.portalnoticias.category.entity.Category;
import com.portalnoticias.videonota.dto.VideoNotaRequest;
import com.portalnoticias.videonota.dto.VideoNotaResponse;
import com.portalnoticias.videonota.entity.VideoNota;

@Component
public class VideoNotaMapper {

    public VideoNota toEntity(
            VideoNotaRequest request,
            Category categoria
    ) {

        return VideoNota.builder()
                .titulo(request.getTitulo())
                .descripcion(request.getDescripcion())
                .categoria(categoria)
                .vistas(0L)
                .activo(true)
                .build();
    }

    public VideoNotaResponse toResponse(
            VideoNota video
    ) {

        Category categoria = video.getCategoria();

        return VideoNotaResponse.builder()
                .id(video.getId())
                .titulo(video.getTitulo())
                .descripcion(video.getDescripcion())
                .videoUrl(video.getVideoUrl())
                .thumbnailUrl(video.getThumbnailUrl())
                .vistas(video.getVistas())
                .categoriaId(
                        categoria != null
                                ? categoria.getId()
                                : null
                )
                .categoriaNombre(
                        categoria != null
                                ? categoria.getNombre()
                                : null
                )
                .categoriaSlug(
                        categoria != null
                                ? categoria.getSlug()
                                : null
                )
                .categoriaColor(
                        categoria != null
                                ? categoria.getColor()
                                : null
                )
                .activo(video.getActivo())
                .createdAt(video.getCreatedAt())
                .updatedAt(video.getUpdatedAt())
                .build();
    }
}