package com.portalnoticias.columnist.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColumnistRequest {

    private Long userId;

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 150)
    private String nombre;

    @NotBlank(message = "El slug es obligatorio.")
    @Size(max = 180)
    private String slug;

    @NotBlank(message = "El cargo es obligatorio.")
    @Size(max = 120)
    private String cargo;

    private String biografia;

    private String foto;

    private String facebook;

    private String twitter;

    private String instagram;

    private String linkedin;

    @Email(message = "Correo inválido.")
    private String correo;

    private Boolean activo;
    private Boolean destacado;

private Integer ordenInicio;

}