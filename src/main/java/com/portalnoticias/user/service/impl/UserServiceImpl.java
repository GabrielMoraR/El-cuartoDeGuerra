package com.portalnoticias.user.service.impl;

import java.io.IOException;
import java.util.Map;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.portalnoticias.role.entity.Role;
import com.portalnoticias.role.repository.RoleRepository;
import com.portalnoticias.shared.cloudinary.CloudinaryService;
import com.portalnoticias.shared.exception.ConflictException;
import com.portalnoticias.shared.exception.ResourceNotFoundException;
import com.portalnoticias.shared.pagination.PageMapper;
import com.portalnoticias.shared.pagination.PageResponse;
import com.portalnoticias.user.dto.UserRequest;
import com.portalnoticias.user.dto.UserResponse;
import com.portalnoticias.user.entity.User;
import com.portalnoticias.user.mapper.UserMapper;
import com.portalnoticias.user.repository.UserRepository;
import com.portalnoticias.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    private final RoleRepository roleRepository;

    private final UserMapper mapper;

    private final CloudinaryService cloudinaryService;

    @Override
    public PageResponse findAll(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        Page<User> users =
                repository.findAll(pageable);

        Page<UserResponse> response =
                users.map(mapper::toResponse);

        return PageMapper.from(response);
    }

    @Override
    public UserResponse findById(Long id) {

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "El usuario no existe."
                        ));

        return mapper.toResponse(user);
    }

    @Override
    public UserResponse save(UserRequest request) {

        if (repository.existsByUsername(
                request.getUsername())) {

            throw new ConflictException(
                    "Ya existe un usuario con ese nombre."
            );
        }

        if (repository.existsByEmail(
                request.getEmail())) {

            throw new ConflictException(
                    "Ya existe un usuario con ese correo."
            );
        }

        Role role = roleRepository
                .findById(request.getRoleId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "El rol no existe."
                        ));

        User user =
                mapper.toEntity(request, role);

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setActive(true);

        user = repository.save(user);

        return mapper.toResponse(user);
    }

    @Override
    public UserResponse update(
            Long id,
            UserRequest request) {

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "El usuario no existe."
                        ));

        repository.findByUsername(
                request.getUsername()
        ).ifPresent(existing -> {

            if (!existing.getId().equals(id)) {

                throw new ConflictException(
                        "Ya existe un usuario con ese nombre."
                );
            }
        });

        repository.findByEmail(
                request.getEmail()
        ).ifPresent(existing -> {

            if (!existing.getId().equals(id)) {

                throw new ConflictException(
                        "Ya existe un usuario con ese correo."
                );
            }
        });

        Role role = roleRepository
                .findById(request.getRoleId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "El rol no existe."
                        ));

        user.setRole(role);

        user.setNombre(
                request.getNombre()
        );

        user.setApellidoPaterno(
                request.getApellidoPaterno()
        );

        user.setApellidoMaterno(
                request.getApellidoMaterno()
        );

        user.setUsername(
                request.getUsername()
        );

        user.setEmail(
                request.getEmail()
        );

        if (request.getPassword() != null
                && !request.getPassword().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(
                            request.getPassword()
                    )
            );
        }

        user = repository.save(user);

        return mapper.toResponse(user);
    }

    @Override
    public UserResponse uploadPhoto(
            Long id,
            MultipartFile file) {

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "El usuario no existe."
                        ));

        if (file == null || file.isEmpty()) {

            throw new IllegalArgumentException(
                    "La imagen es obligatoria."
            );
        }

        try {

            /*
             * Guardamos la referencia anterior
             * antes de subir la nueva.
             */
            String oldPublicId =
                    user.getPhotoPublicId();

            /*
             * Subimos la nueva imagen.
             */
            Map uploadResult =
                    cloudinaryService.upload(file);

            String url =
                    uploadResult
                            .get("secure_url")
                            .toString();

            String publicId =
                    uploadResult
                            .get("public_id")
                            .toString();

            /*
             * Actualizamos usuario.
             */
            user.setPhoto(url);

            user.setPhotoPublicId(publicId);

            user = repository.save(user);

            /*
             * Eliminamos la imagen anterior
             * después de guardar correctamente
             * la nueva.
             */
            if (oldPublicId != null
                    && !oldPublicId.isBlank()) {

                cloudinaryService.delete(
                        oldPublicId
                );
            }

            return mapper.toResponse(user);

        } catch (IOException e) {

            throw new RuntimeException(
                    "No fue posible subir la imagen.",
                    e
            );
        }
    }

    @Override
    public void delete(Long id) {

        User user = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "El usuario no existe."
                        ));

        /*
         * Eliminamos primero la imagen
         * asociada al usuario.
         */
        if (user.getPhotoPublicId() != null
                && !user.getPhotoPublicId().isBlank()) {

            try {

                cloudinaryService.delete(
                        user.getPhotoPublicId()
                );

            } catch (IOException e) {

                throw new RuntimeException(
                        "No fue posible eliminar la foto del usuario.",
                        e
                );
            }
        }

        repository.delete(user);
    }
}