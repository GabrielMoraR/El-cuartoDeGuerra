package com.portalnoticias.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequest {

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 100)
    private String nombre;

    @NotBlank(message = "El slug es obligatorio.")
    @Size(max = 120)
    private String slug;

    @Size(max = 255)
    private String descripcion;

    private String color;

    private String icono;

}