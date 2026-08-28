package com.portalnoticias.role.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.portalnoticias.role.dto.PermissionResponse;
import com.portalnoticias.role.dto.RoleRequest;
import com.portalnoticias.role.dto.RoleResponse;
import com.portalnoticias.role.entity.Permission;
import com.portalnoticias.role.entity.Role;

@Component
public class RoleMapper {


    // ============================================================
    // REQUEST -> ENTITY
    // ============================================================

    public Role toEntity(RoleRequest request) {

        return Role.builder()

                .name(
                        request.getName()
                )

                .description(
                        request.getDescription()
                )

                .build();

    }


    // ============================================================
    // ENTITY -> RESPONSE
    // ============================================================

    public RoleResponse toResponse(Role role) {

        Set<PermissionResponse> permissions =

                role.getPermissions()
                        .stream()
                        .map(this::toPermissionResponse)
                        .collect(Collectors.toSet());


        return RoleResponse.builder()

                .id(
                        role.getId()
                )

                .name(
                        role.getName()
                )

                .description(
                        role.getDescription()
                )

                .permissions(
                        permissions
                )

                .build();

    }


    // ============================================================
    // PERMISSION -> PERMISSION RESPONSE
    // ============================================================

    private PermissionResponse toPermissionResponse(
            Permission permission
    ) {

        return PermissionResponse.builder()

                .id(
                        permission.getId()
                )

                .code(
                        permission.getCode()
                )

                .name(
                        permission.getName()
                )

                .description(
                        permission.getDescription()
                )

                .module(
                        permission.getModule()
                )

                .build();

    }

}