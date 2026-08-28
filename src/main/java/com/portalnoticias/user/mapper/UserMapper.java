package com.portalnoticias.user.mapper;

import org.springframework.stereotype.Component;

import com.portalnoticias.role.entity.Role;
import com.portalnoticias.user.dto.UserRequest;
import com.portalnoticias.user.dto.UserResponse;
import com.portalnoticias.user.entity.User;

@Component
public class UserMapper {

    public User toEntity(UserRequest request, Role role){

        return User.builder()
                .role(role)
                .nombre(request.getNombre())
                .apellidoPaterno(request.getApellidoPaterno())
                .apellidoMaterno(request.getApellidoMaterno())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .active(true)
                .build();

    }

    public UserResponse toResponse(User user){

        return UserResponse.builder()
                .id(user.getId())
                .roleId(user.getRole().getId())
                .roleName(user.getRole().getName())
                .nombre(user.getNombre())
                .apellidoPaterno(user.getApellidoPaterno())
                .apellidoMaterno(user.getApellidoMaterno())
                .username(user.getUsername())
                .email(user.getEmail())
                .photo(user.getPhoto())
                .active(user.getActive())
                .lastLogin(user.getLastLogin())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();

    }

}