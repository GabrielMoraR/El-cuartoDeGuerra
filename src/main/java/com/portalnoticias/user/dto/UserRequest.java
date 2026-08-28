package com.portalnoticias.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    @NotNull(message = "El rol es obligatorio.")
    private Long roleId;

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(max = 100)
    private String nombre;

    @Size(max = 100)
    private String apellidoPaterno;

    @Size(max = 100)
    private String apellidoMaterno;

    @NotBlank(message = "El usuario es obligatorio.")
    @Size(min = 4, max = 80)
    private String username;

    @NotBlank(message = "El correo es obligatorio.")
    @Email(message = "Correo inválido.")
    @Size(max = 150)
    private String email;

    
    private String password;

}