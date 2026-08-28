package com.portalnoticias.role.dto;

import java.util.Set;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponse {

    private Long id;

    private String name;

    private String description;

    private Set<PermissionResponse> permissions;

}