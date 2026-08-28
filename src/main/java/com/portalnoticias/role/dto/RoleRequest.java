package com.portalnoticias.role.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleRequest {

    @NotBlank(message = "El nombre es obligatorio.")
    @Size(
            min = 3,
            max = 50,
            message = "El nombre debe tener entre 3 y 50 caracteres."
    )
    private String name;

    @Size(
            max = 255,
            message = "La descripción no puede exceder 255 caracteres."
    )
    private String description;
    private Set<Long> permissionIds;

}