package com.portalnoticias.category.entity;

import com.portalnoticias.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categorias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 120)
    private String slug;

    @Column(length = 255)
    private String descripcion;

    @Column(length = 20)
    private String color;

    @Column(length = 100)
    private String icono;

    @Builder.Default
    private Boolean activo = true;

}