package com.portalnoticias.role.config;

import com.portalnoticias.role.entity.Permission;
import com.portalnoticias.role.repository.PermissionRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PermissionInitializer
        implements CommandLineRunner {

    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) {

        List<Permission> permissions = List.of(

            permission(
                "DASHBOARD_VIEW",
                "Ver Dashboard",
                "Dashboard",
                "DASHBOARD"
            ),

            permission(
                "STATISTICS_VIEW",
                "Ver Estadísticas",
                "Consultar estadísticas",
                "DASHBOARD"
            ),

            permission(
                "USERS_VIEW",
                "Ver Usuarios",
                "Consultar usuarios",
                "USUARIOS"
            ),

            permission(
                "USERS_CREATE",
                "Crear Usuarios",
                "Crear usuarios",
                "USUARIOS"
            ),

            permission(
                "USERS_EDIT",
                "Editar Usuarios",
                "Editar usuarios",
                "USUARIOS"
            ),

            permission(
                "USERS_DELETE",
                "Eliminar Usuarios",
                "Eliminar usuarios",
                "USUARIOS"
            ),

            permission(
                "ROLES_VIEW",
                "Ver Roles",
                "Consultar roles",
                "ROLES"
            ),

            permission(
                "ROLES_CREATE",
                "Crear Roles",
                "Crear roles",
                "ROLES"
            ),

            permission(
                "ROLES_EDIT",
                "Editar Roles",
                "Editar roles",
                "ROLES"
            ),

            permission(
                "ROLES_DELETE",
                "Eliminar Roles",
                "Eliminar roles",
                "ROLES"
            ),

            permission(
                "ROLES_ASSIGN",
                "Asignar Permisos",
                "Asignar permisos a roles",
                "ROLES"
            ),

            permission(
                "NEWS_VIEW",
                "Ver Noticias",
                "Consultar noticias",
                "NOTICIAS"
            ),

            permission(
                "NEWS_CREATE",
                "Crear Noticias",
                "Crear noticias",
                "NOTICIAS"
            ),

            permission(
                "NEWS_EDIT",
                "Editar Noticias",
                "Editar noticias",
                "NOTICIAS"
            ),

            permission(
                "NEWS_DELETE",
                "Eliminar Noticias",
                "Eliminar noticias",
                "NOTICIAS"
            ),

            permission(
                "COLUMNISTS_VIEW",
                "Ver Columnistas",
                "Consultar columnistas",
                "COLUMNISTAS"
            ),

            permission(
                "COLUMNISTS_CREATE",
                "Crear Columnistas",
                "Crear columnistas",
                "COLUMNISTAS"
            ),

            permission(
                "COLUMNISTS_EDIT",
                "Editar Columnistas",
                "Editar columnistas",
                "COLUMNISTAS"
            ),

            permission(
                "COLUMNISTS_DELETE",
                "Eliminar Columnistas",
                "Eliminar columnistas",
                "COLUMNISTAS"
            ),

            permission(
                "COLUMNS_VIEW",
                "Ver Columnas",
                "Consultar columnas",
                "COLUMNAS"
            ),

            permission(
                "COLUMNS_CREATE",
                "Crear Columnas",
                "Crear columnas",
                "COLUMNAS"
            ),

            permission(
                "COLUMNS_EDIT",
                "Editar Columnas",
                "Editar columnas",
                "COLUMNAS"
            ),

            permission(
                "COLUMNS_DELETE",
                "Eliminar Columnas",
                "Eliminar columnas",
                "COLUMNAS"
            ),

            permission(
                "ADS_VIEW",
                "Ver Publicidad",
                "Consultar publicidad",
                "PUBLICIDAD"
            ),

            permission(
                "ADS_CREATE",
                "Crear Publicidad",
                "Crear publicidad",
                "PUBLICIDAD"
            ),

            permission(
                "ADS_EDIT",
                "Editar Publicidad",
                "Editar publicidad",
                "PUBLICIDAD"
            ),

            permission(
                "ADS_DELETE",
                "Eliminar Publicidad",
                "Eliminar publicidad",
                "PUBLICIDAD"
            ),
            permission(
    "VIDEOS_VIEW",
    "Ver Video Notas",
    "Consultar video notas",
    "VIDEOS"
),

permission(
    "VIDEOS_CREATE",
    "Crear Video Notas",
    "Crear video notas",
    "VIDEOS"
),

permission(
    "VIDEOS_EDIT",
    "Editar Video Notas",
    "Editar video notas",
    "VIDEOS"
),

permission(
    "VIDEOS_DELETE",
    "Eliminar Video Notas",
    "Eliminar video notas",
    "VIDEOS"
)
        );

        permissions.forEach(permission -> {

            if (!permissionRepository
                    .existsByCode(permission.getCode())) {

                permissionRepository.save(permission);

            }

        });
    }

    private Permission permission(
            String code,
            String name,
            String description,
            String module
    ) {

        return Permission.builder()
                .code(code)
                .name(name)
                .description(description)
                .module(module)
                .build();
    }
}