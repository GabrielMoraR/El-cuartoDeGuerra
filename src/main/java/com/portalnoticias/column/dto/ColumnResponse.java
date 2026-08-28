package com.portalnoticias.column.dto;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColumnResponse {

    private Long id;

    private Long columnistId;

    private String columnistName;

    private String columnistPhoto;

    private String titulo;

    private String slug;

    private String resumen;

    private String contenido;

    private String imagenPrincipal;

    private Long vistas;
   
    private String estado;

    private LocalDateTime fechaPublicacion;
    
    // NUEVO: Enviamos la fecha otorgada por separado por si el admin la quiere editar
    private LocalDateTime fechaOtorgada;

    // NUEVO: Fecha final simplificada para mostrar al público (la otorgada o la de publicación)
    private LocalDateTime fechaEfectiva;
    
    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaActualizacion;

}
