package com.portalnoticias.directory.entity;

import com.portalnoticias.shared.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "directorio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Directory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name = "nombre",
        nullable = false,
        length = 150
    )
    private String nombre;

    @Column(
        name = "cargo",
        nullable = false,
        length = 150
    )
    private String cargo;

    @Column(
        name = "seccion",
        length = 150
    )
    private String seccion;

    @Column(
        name = "correo",
        length = 150
    )
    private String correo;

    @Column(
        name = "celular",
        length = 30
    )
    private String celular;

    @Column(
        name = "foto",
        length = 500
    )
    private String foto;

    @Column(
        name = "activo",
        nullable = false
    )
    private Boolean activo;

    @Column(
        name = "orden",
        nullable = false
    )
    private Integer orden;
}