package com.portalnoticias.columnist.service.impl;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.portalnoticias.columnist.dto.ColumnistRequest;
import com.portalnoticias.columnist.dto.ColumnistResponse;
import com.portalnoticias.columnist.entity.Columnist;
import com.portalnoticias.columnist.mapper.ColumnistMapper;
import com.portalnoticias.columnist.repository.ColumnistRepository;
import com.portalnoticias.columnist.service.ColumnistService;
import com.portalnoticias.shared.cloudinary.CloudinaryService;
import com.portalnoticias.shared.exception.ConflictException;
import com.portalnoticias.shared.exception.ResourceNotFoundException;
import com.portalnoticias.shared.pagination.PageMapper;
import com.portalnoticias.shared.pagination.PageResponse;
import com.portalnoticias.user.entity.User;
import com.portalnoticias.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ColumnistServiceImpl implements ColumnistService {

    private final ColumnistRepository repository;
    private final CloudinaryService cloudinaryService;

    private final UserRepository userRepository;

    private final ColumnistMapper mapper;

    @Override
    public PageResponse<ColumnistResponse> findAll(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Columnist> result = repository.findAll(pageable);

        Page<ColumnistResponse> response = result.map(mapper::toResponse);

        return PageMapper.from(response);
    }

    @Override
    public ColumnistResponse findById(Long id) {

        Columnist columnist = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("El columnista no existe."));

        return mapper.toResponse(columnist);

    }

    @Override
    public ColumnistResponse save(ColumnistRequest request) {

        if (repository.existsBySlug(request.getSlug())) {
            throw new ConflictException("Ya existe un columnista con ese slug.");
        }

        if (repository.existsByCorreo(request.getCorreo())) {
            throw new ConflictException("Ya existe un columnista con ese correo.");
        }

        if (repository.existsByUserId(request.getUserId())) {
            throw new ConflictException("Ese usuario ya tiene asignado un perfil de columnista.");
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("El usuario no existe."));

        Columnist columnist = mapper.toEntity(request, user);

        columnist.setActivo(true);

        columnist = repository.save(columnist);

        return mapper.toResponse(columnist);

    }

    @Override
    public ColumnistResponse update(Long id, ColumnistRequest request) {

        Columnist columnist = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("El columnista no existe."));

        repository.findBySlug(request.getSlug())
                .ifPresent(existing -> {

                    if (!existing.getId().equals(id)) {

                        throw new ConflictException("Ya existe un columnista con ese slug.");

                    }

                });

        repository.findByCorreo(request.getCorreo())
                .ifPresent(existing -> {

                    if (!existing.getId().equals(id)) {

                        throw new ConflictException("Ya existe un columnista con ese correo.");

                    }

                });

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("El usuario no existe."));

        columnist.setNombre(request.getNombre());
        columnist.setCorreo(request.getCorreo());
        columnist.setBiografia(request.getBiografia());
        columnist.setFoto(request.getFoto());
        columnist.setSlug(request.getSlug());
        columnist.setFacebook(request.getFacebook());
        columnist.setInstagram(request.getInstagram());
        columnist.setTwitter(request.getTwitter());
        columnist.setUser(user);

        columnist = repository.save(columnist);

        return mapper.toResponse(columnist);

    }

    @Override
    public void delete(Long id) {

        Columnist columnist = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("El columnista no existe."));

        repository.delete(columnist);

    }
    @Override
public ColumnistResponse uploadPhoto(Long id, MultipartFile file) {

    try {

        Columnist columnist = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("El columnista no existe."));

        Map upload = cloudinaryService.upload(file);

        String imageUrl = upload.get("secure_url").toString();

        columnist.setFoto(imageUrl);

        repository.save(columnist);

        return mapper.toResponse(columnist);

    } catch (Exception e) {

        throw new RuntimeException(e);

    }

}
@Override
public void deletePhoto(Long id) {

    Columnist columnist = repository.findById(id)
            .orElseThrow(() ->
                    new ResourceNotFoundException("El columnista no existe."));

    columnist.setFoto(null);

    repository.save(columnist);

}
    

}