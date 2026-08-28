package com.portalnoticias.role.service;

import java.util.List;
import java.util.Set;

import com.portalnoticias.role.dto.RoleRequest;
import com.portalnoticias.role.dto.RoleResponse;

public interface RoleService {

    List<RoleResponse> findAll();

    RoleResponse findById(Long id);

    RoleResponse save(RoleRequest request);

    RoleResponse update(Long id, RoleRequest request);

    RoleResponse updatePermissions(
            Long id,
            Set<Long> permissionIds
    );

    void delete(Long id);

}