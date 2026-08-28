package com.portalnoticias.news.dto;

import lombok.Builder;
import lombok.Data;
import com.portalnoticias.autores.dto.response.AuthorResponse;
import java.time.LocalDateTime;

@Data
@Builder
public class NewsResponse {

    private Long id;

    private Long categoryId;
    private String categoryName;
    private AuthorResponse author;
    private String titulo;

    private String slug;

    private String resumen;

    private String contenido;

    private String imagenPrincipal;

    private Boolean destacada;

    private Boolean publicada;

    private Integer vistas;

    private String seoTitle;

    private String seoDescription;

    private LocalDateTime fechaPublicacion;

    private String imagenPortada;

    private String categoryColor;
    
    private String categoryIcon;
}
