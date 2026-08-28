package com.portalnoticias.role.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionResponse {

    private Long id;

    private String code;

    private String name;

    private String description;

    private String module;

}