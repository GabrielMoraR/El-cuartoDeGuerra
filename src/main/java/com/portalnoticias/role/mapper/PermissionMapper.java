package com.portalnoticias.role.mapper;

import org.springframework.stereotype.Component;

import com.portalnoticias.role.dto.PermissionRequest;
import com.portalnoticias.role.dto.PermissionResponse;
import com.portalnoticias.role.entity.Permission;

@Component
public class PermissionMapper {

    public Permission toEntity(PermissionRequest request) {

        return Permission.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .module(request.getModule())
                .build();

    }

    public PermissionResponse toResponse(Permission permission) {

        return PermissionResponse.builder()
                .id(permission.getId())
                .code(permission.getCode())
                .name(permission.getName())
                .description(permission.getDescription())
                .module(permission.getModule())
                .build();

    }

}