package com.portalnoticias.videonota.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoNotaRequest {

    @NotBlank(message = "El título es obligatorio.")
    @Size(max = 200)
    private String titulo;

    @Size(max = 500)
    private String descripcion;

    @NotNull(message = "La categoría es obligatoria.")
    private Long categoriaId;
}