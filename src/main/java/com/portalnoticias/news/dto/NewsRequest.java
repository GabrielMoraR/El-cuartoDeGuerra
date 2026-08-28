package com.portalnoticias.news.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewsRequest {

    @NotNull(message = "La categoría es obligatoria.")
    private Long categoryId;

    

    @NotBlank(message = "El título es obligatorio.")
    private String titulo;

    @NotBlank(message = "El slug es obligatorio.")
    private String slug;

    private String resumen;

    @NotBlank(message = "El contenido es obligatorio.")
    private String contenido;

    private String imagenPrincipal;

    private Boolean destacada;

    private Boolean publicada;

    private String seoTitle;

    private String seoDescription;

    private LocalDateTime fechaPublicacion;

    private String imagenPortada;
    private Long authorId;
}
