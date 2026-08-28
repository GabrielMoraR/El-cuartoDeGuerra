package com.portalnoticias.user.entity;

import java.time.LocalDateTime;

import com.portalnoticias.role.entity.Role;
import com.portalnoticias.shared.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id", nullable = false)
    private Role role;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido_paterno", length = 100)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", length = 100)
    private String apellidoMaterno;

    @Column(name = "usuario", nullable = false, unique = true, length = 80)
    private String username;

    @Column(name = "correo", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "foto_perfil", length = 500)
    private String photo;

    @Column(name = "foto_public_id", length = 255)
    private String photoPublicId;

    @Column(name = "activo")
    private Boolean active;

    @Column(name = "ultimo_login")
    private LocalDateTime lastLogin;
}