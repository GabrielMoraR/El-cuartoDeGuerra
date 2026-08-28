package com.portalnoticias.importantnews.entity;

import java.time.LocalDateTime;

import com.portalnoticias.shared.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "noticias_urgentes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportantNews extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, length = 500)
    private String mensaje;

    @Column(length = 50)
    private String tipo;

    private Boolean activo;

    private Integer orden;

    @Column(name = "tiempo_mostrar")
    private Integer tiempoMostrar;

    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDateTime fechaFin;
}
