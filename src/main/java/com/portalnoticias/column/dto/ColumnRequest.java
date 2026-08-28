package com.portalnoticias.column.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColumnRequest {

    @NotNull
    private Long columnistId;

    @NotBlank
    private String titulo;

    @NotBlank
    private String slug;

    private String resumen;

    private String contenido;

    private String imagenPrincipal;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "BORRADOR|PUBLICADA|ARCHIVADA", message = "El estado debe ser BORRADOR, PUBLICADA o ARCHIVADA")
    private String estado;

    private LocalDateTime fechaPublicacion;

    // NUEVO CAMPO RECIBIDO DESDE EL FRONT-END
    private LocalDateTime fechaOtorgada;

    /**
     * Valida de manera explícita si la petición cumple con la regla de exclusión mutua.
     * Retorna verdadero si solo una de las dos fechas viene con datos.
     */
    public boolean hasValidDateExclusion() {
        if (this.fechaPublicacion != null && this.fechaOtorgada != null) {
            return false; // Error: Ambas están activas
        }
        return true;
    }
}
