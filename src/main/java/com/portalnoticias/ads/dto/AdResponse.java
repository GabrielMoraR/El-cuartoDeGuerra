package com.portalnoticias.ads.dto;

import com.portalnoticias.ads.entity.AdLocation;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdResponse {

    private Long id;

    private String titulo;

    private String imagen;

    private String enlace;

    private AdLocation ubicacion;

    private Boolean activo;

    private Integer orden;

    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}