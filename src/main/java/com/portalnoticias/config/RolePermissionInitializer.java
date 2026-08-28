package com.portalnoticias.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.portalnoticias.role.entity.Permission;
import com.portalnoticias.role.entity.Role;
import com.portalnoticias.role.repository.PermissionRepository;
import com.portalnoticias.role.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RolePermissionInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;


    @Override
    @Transactional
    public void run(String... args) {

        System.out.println(
                "=============================================="
        );
        System.out.println(
                " INICIALIZANDO ROLES Y PERMISOS EXPANDIDOS"
        );
        System.out.println(
                "=============================================="
        );

        assignPermissions(
                "ADMINISTRADOR",
                Set.of(
                        "DASHBOARD_VIEW",
                        "STATISTICS_VIEW",

                        "USERS_VIEW",
                        "USERS_CREATE",
                        "USERS_EDIT",
                        "USERS_DELETE",

                        "ROLES_VIEW",
                        "ROLES_CREATE",
                        "ROLES_EDIT",
                        "ROLES_DELETE",
                        "ROLES_ASSIGN",

                        // CORRECCIÓN: Permisos granulares de Noticias ordinarias
                        "NEWS_VIEW",
                        "NEWS_CREATE",
                        "NEWS_EDIT",
                        "NEWS_DELETE",

                        // CORRECCIÓN: Permisos granulares de Noticias Urgentes
                        "IMPORTANT_VIEW",
                        "IMPORTANT_CREATE",
                        "IMPORTANT_EDIT",
                        "IMPORTANT_DELETE",

                        "COLUMNISTS_VIEW",
                        "COLUMNISTS_CREATE",
                        "COLUMNISTS_EDIT",
                        "COLUMNISTS_DELETE",

                        "COLUMNS_VIEW",
                        "COLUMNS_CREATE",
                        "COLUMNS_EDIT",
                        "COLUMNS_DELETE",

                        "ADS_VIEW",
                        "ADS_CREATE",
                        "ADS_EDIT",
                        "ADS_DELETE",

                        "VIDEOS_VIEW",
                        "VIDEOS_CREATE",
                        "VIDEOS_EDIT",
                        "VIDEOS_DELETE",

                        "DIRECTORY_VIEW",
                        "DIRECTORY_CREATE",
                        "DIRECTORY_EDIT",
                        "DIRECTORY_DELETE"
                )
        );
        
        assignPermissions(
                "COLUMNISTA",
                Set.of(
                        "COLUMNISTS_VIEW",
                        "COLUMNISTS_CREATE",
                        "COLUMNISTS_EDIT",
                        "COLUMNISTS_DELETE",

                        "COLUMNS_VIEW",
                        "COLUMNS_CREATE",
                        "COLUMNS_EDIT",
                        "COLUMNS_DELETE"
                )
        );
        assignPermissions(
                "PUBLICISTA",
                Set.of(
                        "ADS_VIEW",
                        "ADS_CREATE",
                        "ADS_EDIT",
                        "ADS_DELETE"
                )
        );

        assignPermissions(
                "OPERADOR",
                Set.of(
                        "DASHBOARD_VIEW",
                        "STATISTICS_VIEW",
                        
                        // El operador puede supervisar el feed de noticias comunes y urgentes
                        "NEWS_VIEW",
                        "IMPORTANT_VIEW",
                        "VIDEOS_VIEW",
                        "ADS_VIEW",
                        "DIRECTORY_VIEW"
                )
        );

        System.out.println(
                "=============================================="
        );
        System.out.println(
                " ROLES Y PERMISOS INICIALIZADOS CORRECTAMENTE"
        );
        System.out.println(
                "=============================================="
        );
    }


    private void assignPermissions(
            String roleName,
            Set<String> permissionCodes
    ) {

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() ->
                        new RuntimeException(
                                "No existe el rol: " + roleName
                        )
                );

        Set<Permission> permissions = new HashSet<>();

        for (String code : permissionCodes) {

            Permission permission =
                    permissionRepository.findByCode(code)
                            .orElseGet(() -> {
                                
                                // El extractor lee 'NEWS' o 'IMPORTANT' de forma limpia
                                String moduloCalculado = "GENERAL";
                                if (code.contains("_")) {
                                    moduloCalculado = code.substring(0, code.indexOf("_")).toUpperCase();
                                }

                                Permission newPermission = new Permission();
                                newPermission.setCode(code);
                                newPermission.setName(code.replace("_", " "));
                                newPermission.setModule(moduloCalculado); 
                                newPermission.setDescription("Permiso para " + code.replace("_", " ").toLowerCase());

                                return permissionRepository.save(newPermission);
                            });

            permissions.add(permission);
        }
        
        role.setPermissions(permissions);
        roleRepository.save(role);

        System.out.println(
                "✓ "
                + roleName
                + " → "
                + permissions.size()
                + " permisos sincronizados con éxito."
        );
    }
}
