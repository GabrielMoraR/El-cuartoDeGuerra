package com.portalnoticias.role.entity;

import java.util.HashSet;
import java.util.Set;

import com.portalnoticias.shared.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
    name = "roles",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_rol_nombre",
            columnNames = "nombre"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(
        name = "nombre",
        nullable = false,
        unique = true,
        length = 100
    )
    private String name;


    @Column(
        name = "descripcion",
        length = 255
    )
    private String description;


    // ============================================================
    // PERMISOS
    // ============================================================

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "rol_permisos",

        joinColumns = @JoinColumn(
            name = "rol_id"
        ),

        inverseJoinColumns = @JoinColumn(
            name = "permiso_id"
        ),

        uniqueConstraints = {
            @UniqueConstraint(
                name = "uk_rol_permiso",
                columnNames = {
                    "rol_id",
                    "permiso_id"
                }
            )
        }
    )
    @Builder.Default
    private Set<Permission> permissions = new HashSet<>();

}