package com.portalnoticias.directory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirectoryRequest {

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 150)
    private String nombre;

    @NotBlank(message = "El cargo es obligatorio.")
    @Size(max = 150)
    private String cargo;

    @Size(max = 150)
    private String seccion;

    @Size(max = 150)
    private String correo;

    @Size(max = 30)
    private String celular;

    private String foto;

    private Boolean activo;

    private Integer orden;
}