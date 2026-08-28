package com.portalnoticias.role.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portalnoticias.role.dto.RoleRequest;
import com.portalnoticias.role.dto.RoleResponse;
import com.portalnoticias.role.entity.Permission;
import com.portalnoticias.role.entity.Role;
import com.portalnoticias.role.mapper.RoleMapper;
import com.portalnoticias.role.repository.PermissionRepository;
import com.portalnoticias.role.repository.RoleRepository;
import com.portalnoticias.role.service.RoleService;
import com.portalnoticias.shared.exception.ConflictException;
import com.portalnoticias.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository repository;

    private final PermissionRepository permissionRepository;

    private final RoleMapper mapper;


    // ============================================================
    // LISTAR ROLES
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());

    }


    // ============================================================
    // BUSCAR ROL
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public RoleResponse findById(Long id) {

        Role role = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "El rol no existe."
                        )
                );

        return mapper.toResponse(role);

    }


    // ============================================================
    // CREAR ROL
    // ============================================================

    @Override
    public RoleResponse save(RoleRequest request) {

        String roleName =
                normalizeName(request.getName());

        validateName(roleName);

        if (repository.existsByName(roleName)) {

            throw new ConflictException(
                    "Ya existe un rol con ese nombre."
            );
        }

        Role role =
                mapper.toEntity(request);

        role.setName(roleName);

        role.setPermissions(
                resolvePermissions(
                        request.getPermissionIds()
                )
        );

        role = repository.save(role);

        return mapper.toResponse(role);

    }


    // ============================================================
    // ACTUALIZAR ROL
    // ============================================================

    @Override
    public RoleResponse update(
            Long id,
            RoleRequest request
    ) {

        Role role =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "El rol no existe."
                                )
                        );


        String roleName =
                normalizeName(request.getName());

        validateName(roleName);


        // --------------------------------------------------------
        // VALIDAR NOMBRE DUPLICADO
        // --------------------------------------------------------

        repository.findByName(roleName)
                .ifPresent(existing -> {

                    if (!existing.getId().equals(id)) {

                        throw new ConflictException(
                                "Ya existe un rol con ese nombre."
                        );

                    }

                });


        // --------------------------------------------------------
        // ROLES DEL SISTEMA
        // --------------------------------------------------------

        if (
                isSystemRole(role.getName())
                && !role.getName().equals(roleName)
        ) {

            throw new ConflictException(
                    "Los roles del sistema no pueden cambiar su nombre."
            );

        }


        // --------------------------------------------------------
        // ACTUALIZAR DATOS
        // --------------------------------------------------------

        role.setName(roleName);

        role.setDescription(
                request.getDescription()
        );


        // --------------------------------------------------------
        // ACTUALIZAR PERMISOS
        // --------------------------------------------------------

        role.setPermissions(
                resolvePermissions(
                        request.getPermissionIds()
                )
        );


        role = repository.save(role);

        return mapper.toResponse(role);

    }


    // ============================================================
    // ASIGNAR / ACTUALIZAR PERMISOS
    // ============================================================

    @Override
    public RoleResponse updatePermissions(
            Long id,
            Set<Long> permissionIds
    ) {

        Role role =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "El rol no existe."
                                )
                        );


        // --------------------------------------------------------
        // RESOLVER PERMISOS
        // --------------------------------------------------------

        Set<Permission> permissions =
                resolvePermissions(permissionIds);


        // --------------------------------------------------------
        // ASIGNAR PERMISOS
        // --------------------------------------------------------

        role.setPermissions(permissions);


        // --------------------------------------------------------
        // GUARDAR
        // --------------------------------------------------------

        role = repository.save(role);


        return mapper.toResponse(role);

    }


    // ============================================================
    // ELIMINAR ROL
    // ============================================================

    @Override
    public void delete(Long id) {

        Role role =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "El rol no existe."
                                )
                        );


        if (isSystemRole(role.getName())) {

            throw new ConflictException(
                    "Los roles del sistema no pueden eliminarse."
            );

        }


        repository.delete(role);

    }


    // ============================================================
    // RESOLVER PERMISOS
    // ============================================================

    private Set<Permission> resolvePermissions(
            Set<Long> permissionIds
    ) {

        if (
                permissionIds == null
                || permissionIds.isEmpty()
        ) {

            return new HashSet<>();

        }


        List<Permission> permissions =
                permissionRepository.findAllById(
                        permissionIds
                );


        Set<Long> foundIds =
                permissions
                        .stream()
                        .map(Permission::getId)
                        .collect(Collectors.toSet());


        Set<Long> missingIds =
                new HashSet<>(permissionIds);

        missingIds.removeAll(foundIds);


        if (!missingIds.isEmpty()) {

            throw new ResourceNotFoundException(
                    "Uno o más permisos no existen: "
                            + missingIds
            );

        }


        return new HashSet<>(permissions);

    }


    // ============================================================
    // NORMALIZAR NOMBRE
    // ============================================================

    private String normalizeName(String name) {

        if (name == null) {

            return "";

        }

        return name
                .trim()
                .toUpperCase();

    }


    // ============================================================
    // VALIDAR NOMBRE
    // ============================================================

    private void validateName(String name) {

        if (name.isBlank()) {

            throw new ConflictException(
                    "El nombre del rol es obligatorio."
            );

        }

    }


    // ============================================================
    // ROLES DEL SISTEMA
    // ============================================================

    private boolean isSystemRole(String name) {

        return "ADMINISTRADOR".equals(name)
                || "OPERADOR".equals(name)
                || "COLUMNISTA".equals(name)
                || "PUBLICISTA".equals(name);

    }

}