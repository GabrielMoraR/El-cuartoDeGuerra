package com.portalnoticias.columnist.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColumnistResponse {

    private Long id;

    private Long userId;

    private String nombre;

    private String slug;

    private String cargo;

    private String biografia;

    private String foto;

    private String facebook;

    private String twitter;

    private String instagram;

    private String linkedin;

    private String correo;

    private Boolean activo;

}