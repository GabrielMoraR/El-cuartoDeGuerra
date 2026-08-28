package com.portalnoticias.user.service;

import org.springframework.web.multipart.MultipartFile;

import com.portalnoticias.shared.pagination.PageResponse;
import com.portalnoticias.user.dto.UserRequest;
import com.portalnoticias.user.dto.UserResponse;

public interface UserService {

    PageResponse findAll(
            int page,
            int size,
            String sortBy,
            String direction
    );

    UserResponse findById(Long id);

    UserResponse save(UserRequest request);

    UserResponse update(Long id, UserRequest request);

    UserResponse uploadPhoto(
            Long id,
            MultipartFile file
    );

    void delete(Long id);
}