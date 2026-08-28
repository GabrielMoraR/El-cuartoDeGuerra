package com.portalnoticias.directory.dto;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirectoryResponse {

    private Long id;

    private String nombre;

    private String cargo;

    private String seccion;

    private String correo;

    private String celular;

    private String foto;

    private Boolean activo;

    private Integer orden;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}