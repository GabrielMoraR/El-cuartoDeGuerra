package com.portalnoticias.category.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {

    private Long id;

    private String nombre;

    private String slug;

    private String descripcion;

    private String color;

    private String icono;

    private Boolean activo;

}