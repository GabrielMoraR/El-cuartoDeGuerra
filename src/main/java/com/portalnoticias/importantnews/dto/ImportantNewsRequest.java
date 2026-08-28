package com.portalnoticias.importantnews.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportantNewsRequest {

    @NotBlank(message = "El titulo es obligatorio")
    private String titulo;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    private String tipo;

    private Boolean activo;

    private Integer orden;

    private Integer tiempoMostrar;

    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;
}
