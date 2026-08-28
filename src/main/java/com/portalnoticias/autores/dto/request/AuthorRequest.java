package com.portalnoticias.autores.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthorRequest {
    @NotBlank(message = "El nombre del autor es obligatorio.")
    private String nombre;
}
