package com.portalnoticias.role.entity;

import com.portalnoticias.shared.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "permisos",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_permiso_codigo",
            columnNames = "codigo"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name = "codigo",
        nullable = false,
        unique = true,
        length = 100
    )
    private String code;

    @Column(
        name = "nombre",
        nullable = false,
        length = 100
    )
    private String name;

    @Column(
        name = "descripcion",
        length = 255
    )
    private String description;

    @Column(
        name = "modulo",
        nullable = false,
        length = 50
    )
    private String module;

}