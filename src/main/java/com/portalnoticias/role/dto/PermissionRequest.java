package com.portalnoticias.role.dto;
import java.util.Set;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.validation.constraints.NotNull;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionRequest {

    @NotBlank(message = "El código del permiso es obligatorio.")
    @Size(max = 100, message = "El código no puede exceder 100 caracteres.")
    private String code;

    @NotBlank(message = "El nombre del permiso es obligatorio.")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres.")
    private String name;

    @Size(max = 255, message = "La descripción no puede exceder 255 caracteres.")
    private String description;

    @NotBlank(message = "El módulo es obligatorio.")
    @Size(max = 50, message = "El módulo no puede exceder 50 caracteres.")
    private String module;
     @NotNull(message = "Los permisos son obligatorios.")
    private Set<Long> permissionIds;
}