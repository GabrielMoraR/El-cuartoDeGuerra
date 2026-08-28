package com.portalnoticias.role.service;

import java.util.List;

import com.portalnoticias.role.dto.PermissionRequest;
import com.portalnoticias.role.dto.PermissionResponse;

public interface PermissionService {

    List<PermissionResponse> findAll();

    PermissionResponse findById(Long id);

    PermissionResponse save(PermissionRequest request);

    PermissionResponse update(Long id, PermissionRequest request);

    void delete(Long id);

}