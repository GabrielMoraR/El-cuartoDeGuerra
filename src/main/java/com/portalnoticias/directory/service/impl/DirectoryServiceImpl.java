package com.portalnoticias.directory.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.portalnoticias.directory.dto.DirectoryRequest;
import com.portalnoticias.directory.dto.DirectoryResponse;
import com.portalnoticias.directory.entity.Directory;
import com.portalnoticias.directory.mapper.DirectoryMapper;
import com.portalnoticias.directory.repository.DirectoryRepository;
import com.portalnoticias.directory.service.DirectoryService;
import com.portalnoticias.shared.cloudinary.CloudinaryService;
import com.portalnoticias.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DirectoryServiceImpl
        implements DirectoryService {

    private final DirectoryRepository repository;

    private final DirectoryMapper mapper;

    private final CloudinaryService cloudinaryService;


    @Override
    public List<DirectoryResponse> findAll() {

        return repository.findAll(
                org.springframework.data.domain.Sort
                        .by("orden")
                        .ascending()
        )
        .stream()
        .map(mapper::toResponse)
        .toList();
    }


    @Override
    public List<DirectoryResponse> findActive() {

        return repository
                .findByActivoTrueOrderByOrdenAsc()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }


    @Override
    public DirectoryResponse findById(Long id) {

        Directory directory =
                repository.findById(id)
                        .orElseThrow(
                            () -> new ResourceNotFoundException(
                                "El registro del directorio no existe."
                            )
                        );

        return mapper.toResponse(directory);
    }


    @Override
    public DirectoryResponse save(
            DirectoryRequest request) {

        Directory directory =
                mapper.toEntity(request);

        directory =
                repository.save(directory);

        return mapper.toResponse(directory);
    }


    @Override
    public DirectoryResponse update(
            Long id,
            DirectoryRequest request) {

        Directory directory =
                repository.findById(id)
                        .orElseThrow(
                            () -> new ResourceNotFoundException(
                                "El registro del directorio no existe."
                            )
                        );


        directory.setNombre(
                request.getNombre()
        );

        directory.setCargo(
                request.getCargo()
        );

        directory.setSeccion(
                request.getSeccion()
        );

        directory.setCorreo(
                request.getCorreo()
        );

        directory.setCelular(
                request.getCelular()
        );

        directory.setFoto(
                request.getFoto()
        );

        if (request.getActivo() != null) {

            directory.setActivo(
                    request.getActivo()
            );

        }

        if (request.getOrden() != null) {

            directory.setOrden(
                    request.getOrden()
            );

        }


        directory =
                repository.save(directory);

        return mapper.toResponse(directory);
    }


    @Override
    public void delete(Long id) {

        Directory directory =
                repository.findById(id)
                        .orElseThrow(
                            () -> new ResourceNotFoundException(
                                "El registro del directorio no existe."
                            )
                        );

        repository.delete(directory);
    }


    @Override
    public DirectoryResponse uploadPhoto(
            Long id,
            MultipartFile file) {

        Directory directory =
                repository.findById(id)
                        .orElseThrow(
                            () -> new ResourceNotFoundException(
                                "El registro del directorio no existe."
                            )
                        );

        try {

            Map uploadResult =
                    cloudinaryService.upload(file);

            String url =
                    uploadResult
                        .get("secure_url")
                        .toString();

            directory.setFoto(url);

            directory =
                    repository.save(directory);

            return mapper.toResponse(directory);

        } catch (IOException e) {

            throw new RuntimeException(
                "No fue posible subir la foto.",
                e
            );
        }
    }
}