package com.portalnoticias.columnist.entity;

import com.portalnoticias.role.entity.Role;
import com.portalnoticias.shared.entity.BaseEntity;
import com.portalnoticias.user.entity.User;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "columnistas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Columnist extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private User user;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false, unique = true, length = 180)
    private String slug;

    @Column(nullable = false, length = 120)
    private String cargo;

    @Column(columnDefinition = "TEXT")
    private String biografia;

    @Column(length = 500)
    private String foto;

    @Column(length = 255)
    private String facebook;

    @Column(length = 255)
    private String twitter;

    @Column(length = 255)
    private String instagram;

    @Column(length = 255)
    private String linkedin;

    @Column(length = 150)
    private String correo;

    @Column(nullable = false)
    private Boolean activo;

}