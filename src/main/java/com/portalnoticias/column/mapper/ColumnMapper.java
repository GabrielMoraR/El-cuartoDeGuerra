package com.portalnoticias.column.mapper;

import org.springframework.stereotype.Component;

import com.portalnoticias.column.dto.ColumnRequest;
import com.portalnoticias.column.dto.ColumnResponse;
import com.portalnoticias.column.entity.ColumnArticle;
import com.portalnoticias.columnist.entity.Columnist;

@Component
public class ColumnMapper {

    public ColumnResponse toResponse(ColumnArticle column) {
        if (column == null) {
            return null;
        }

        Long columnistId = null;
        String columnistName = null;
        String columnistPhoto = null;

        if (column.getColumnist() != null) {
            columnistId = column.getColumnist().getId();
            columnistName = column.getColumnist().getNombre();
            columnistPhoto = column.getColumnist().getFoto();
        }

        return ColumnResponse.builder()
                .id(column.getId())
                .columnistId(columnistId)
                .columnistName(columnistName)
                .columnistPhoto(columnistPhoto)
                .titulo(column.getTitulo())
                .slug(column.getSlug())
                .resumen(column.getResumen())
                .contenido(column.getContenido())
                .imagenPrincipal(column.getImagenPrincipal())
                .vistas(column.getVistas())
                .estado(column.getEstado())
                .fechaPublicacion(column.getFechaPublicacion())
                
                // NUEVOS MAPEOS ASOCIADOS A LA FECHA OTORGADA
                .fechaOtorgada(column.getFechaOtorgada())
                .fechaEfectiva(column.getFechaOtorgada() != null ? column.getFechaOtorgada() : column.getFechaPublicacion())
                
                .fechaCreacion(column.getCreatedAt())
                .fechaActualizacion(column.getUpdatedAt())
                .build();
    }

    public ColumnArticle toEntity(ColumnRequest request, Columnist columnist) {
        if (request == null) {
            return null;
        }

        return ColumnArticle.builder()
                .columnist(columnist)
                .titulo(request.getTitulo())
                .slug(request.getSlug())
                .resumen(request.getResumen())
                .contenido(request.getContenido())
                .imagenPrincipal(request.getImagenPrincipal())
                .estado(request.getEstado())
                .vistas(0L)
                
                // REGLA DE EXCLUSIÓN MUTUA AL GUARDAR EN BASE DE DATOS:
                // Si el request trae una fecha otorgada manual, limpiamos la de publicación nativa.
                .fechaOtorgada(request.getFechaOtorgada())
                .fechaPublicacion(request.getFechaOtorgada() != null ? null : request.getFechaPublicacion())
                .build();
    }
}
