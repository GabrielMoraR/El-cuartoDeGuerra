package com.portalnoticias.user.dto;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long id;

    private Long roleId;

    private String roleName;

    private String nombre;

    private String apellidoPaterno;

    private String apellidoMaterno;

    private String username;

    private String email;

    private String photo;

    private Boolean active;

    private LocalDateTime lastLogin;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}