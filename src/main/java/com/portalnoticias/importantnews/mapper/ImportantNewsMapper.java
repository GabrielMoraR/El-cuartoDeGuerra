package com.portalnoticias.importantnews.mapper;

import org.springframework.stereotype.Component;

import com.portalnoticias.importantnews.dto.ImportantNewsRequest;
import com.portalnoticias.importantnews.dto.ImportantNewsResponse;
import com.portalnoticias.importantnews.entity.ImportantNews;

@Component
public class ImportantNewsMapper {

    public ImportantNews toEntity(ImportantNewsRequest request) {
        return ImportantNews.builder()
                .titulo(request.getTitulo())
                .mensaje(request.getMensaje())
                .tipo(request.getTipo())
                .activo(request.getActivo() == null ? true : request.getActivo())
                .orden(request.getOrden() == null ? 0 : request.getOrden())
                .tiempoMostrar(request.getTiempoMostrar() == null ? 5 : request.getTiempoMostrar())
                .fechaInicio(request.getFechaInicio())
                .fechaFin(request.getFechaFin())
                .build();
    }

    public ImportantNewsResponse toResponse(ImportantNews news) {
        return ImportantNewsResponse.builder()
                .id(news.getId())
                .titulo(news.getTitulo())
                .mensaje(news.getMensaje())
                .tipo(news.getTipo())
                .activo(news.getActivo())
                .orden(news.getOrden())
                .tiempoMostrar(news.getTiempoMostrar())
                .fechaInicio(news.getFechaInicio())
                .fechaFin(news.getFechaFin())
                .createdAt(news.getCreatedAt())
                .updatedAt(news.getUpdatedAt())
                .build();
    }
}
