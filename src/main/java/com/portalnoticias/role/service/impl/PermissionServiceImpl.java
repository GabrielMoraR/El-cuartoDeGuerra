package com.portalnoticias.role.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portalnoticias.role.dto.PermissionRequest;
import com.portalnoticias.role.dto.PermissionResponse;
import com.portalnoticias.role.entity.Permission;
import com.portalnoticias.role.mapper.PermissionMapper;
import com.portalnoticias.role.repository.PermissionRepository;
import com.portalnoticias.role.service.PermissionService;
import com.portalnoticias.shared.exception.ConflictException;
import com.portalnoticias.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository repository;

    private final PermissionMapper mapper;


    @Override
    @Transactional(readOnly = true)
    public List<PermissionResponse> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());

    }


    @Override
    @Transactional(readOnly = true)
    public PermissionResponse findById(Long id) {

        Permission permission = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "El permiso no existe."
                        )
                );

        return mapper.toResponse(permission);

    }


    @Override
    public PermissionResponse save(PermissionRequest request) {

        String code = normalizeCode(request.getCode());

        if (repository.existsByCode(code)) {

            throw new ConflictException(
                    "Ya existe un permiso con ese código."
            );

        }

        request.setCode(code);

        Permission permission =
                mapper.toEntity(request);

        permission = repository.save(permission);

        return mapper.toResponse(permission);

    }


    @Override
    public PermissionResponse update(
            Long id,
            PermissionRequest request
    ) {

        Permission permission =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "El permiso no existe."
                                )
                        );


        String code =
                normalizeCode(request.getCode());


        repository.findAll()
                .stream()
                .filter(existing ->
                        existing.getCode().equals(code)
                )
                .findFirst()
                .ifPresent(existing -> {

                    if (!existing.getId().equals(id)) {

                        throw new ConflictException(
                                "Ya existe un permiso con ese código."
                        );

                    }

                });


        permission.setCode(code);

        permission.setName(
                request.getName()
        );

        permission.setDescription(
                request.getDescription()
        );

        permission.setModule(
                request.getModule()
        );


        permission =
                repository.save(permission);


        return mapper.toResponse(permission);

    }


    @Override
    public void delete(Long id) {

        Permission permission =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "El permiso no existe."
                                )
                        );

        repository.delete(permission);

    }


    private String normalizeCode(String code) {

        if (code == null) {

            return "";

        }

        return code
                .trim()
                .toUpperCase();

    }

}