package com.portalnoticias.ads.dto;

import com.portalnoticias.ads.entity.AdLocation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdRequest {

    @NotBlank(message = "El título es obligatorio.")
    private String titulo;

    private String imagen;

    private String enlace;

    @NotNull(message = "La ubicación es obligatoria.")
    private AdLocation ubicacion;

    private Boolean activo;

    private Integer orden;

    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;

}