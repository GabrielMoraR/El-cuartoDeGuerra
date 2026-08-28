package com.portalnoticias.importantnews.dto;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportantNewsResponse {

    private Long id;
    private String titulo;
    private String mensaje;
    private String tipo;
    private Boolean activo;
    private Integer orden;
    private Integer tiempoMostrar;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
