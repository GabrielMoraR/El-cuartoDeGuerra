package com.portalnoticias.column.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.portalnoticias.column.dto.ColumnRequest;
import com.portalnoticias.column.dto.ColumnResponse;
import com.portalnoticias.column.entity.ColumnArticle;
import com.portalnoticias.column.mapper.ColumnMapper;
import com.portalnoticias.column.repository.ColumnRepository;
import com.portalnoticias.column.service.ColumnService;
import com.portalnoticias.columnist.entity.Columnist;
import com.portalnoticias.columnist.repository.ColumnistRepository;
import com.portalnoticias.shared.cloudinary.CloudinaryService;
import com.portalnoticias.shared.exception.ConflictException;
import com.portalnoticias.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ColumnServiceImpl implements ColumnService {

    private final ColumnRepository repository;
    private final ColumnistRepository columnistRepository;
    private final ColumnMapper mapper;
    private final CloudinaryService cloudinaryService;

    @Override
    public List<ColumnResponse> getLatestColumns() {
        Pageable pageable = PageRequest.of(0, 5);
        // CAMBIO: Se invoca la nueva consulta personalizada con COALESCE para ordenar de forma correcta
        return repository
                .findByEstadoOrderByFechaEfectivaDesc("PUBLICADA", pageable)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ColumnResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public ColumnResponse findById(Long id) {
        ColumnArticle column = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La columna no existe."));
        return mapper.toResponse(column);
    }

    @Override
    public ColumnResponse findBySlug(String slug) {
        ColumnArticle column = repository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("La columna no existe."));
        return mapper.toResponse(column);
    }

    @Override
    public ColumnResponse save(ColumnRequest request) {
        // REGLA: Validar exclusión mutua de fechas antes de crear la entidad
        if (request.getFechaPublicacion() != null && request.getFechaOtorgada() != null) {
            throw new IllegalArgumentException("No se pueden definir simultáneamente la fecha de publicación y la fecha otorgada.");
        }

        if (repository.existsBySlug(request.getSlug())) {
            throw new ConflictException("Ya existe una columna con ese slug.");
        }

        Columnist columnist = columnistRepository
                .findById(request.getColumnistId())
                .orElseThrow(() -> new ResourceNotFoundException("El columnista no existe."));

        // El mapper limpia automáticamente la fechaPublicacion si detecta la fechaOtorgada
        ColumnArticle column = mapper.toEntity(request, columnist);

        if (column.getVistas() == null) {
            column.setVistas(0L);
        }

        if (column.getEstado() == null || column.getEstado().isBlank()) {
            column.setEstado("BORRADOR");
        }

        // Si el estado es "PUBLICADA" y no se ingresó una fecha otorgada manual ni una de publicación, se usa la del servidor
        if ("PUBLICADA".equalsIgnoreCase(column.getEstado()) && column.getFechaOtorgada() == null && column.getFechaPublicacion() == null) {
            column.setFechaPublicacion(LocalDateTime.now());
        }

        column = repository.save(column);
        return mapper.toResponse(column);
    }

    @Override
    public ColumnResponse update(Long id, ColumnRequest request) {
        // REGLA: Validar exclusión mutua de fechas antes de actualizar la entidad
        if (request.getFechaPublicacion() != null && request.getFechaOtorgada() != null) {
            throw new IllegalArgumentException("No se pueden definir simultáneamente la fecha de publicación y la fecha otorgada.");
        }

        ColumnArticle column = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La columna no existe."));

        repository.findBySlug(request.getSlug())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new ConflictException("Ya existe una columna con ese slug.");
                    }
                });

        Columnist columnist = columnistRepository
                .findById(request.getColumnistId())
                .orElseThrow(() -> new ResourceNotFoundException("El columnista no existe."));

        column.setColumnist(columnist);
        column.setTitulo(request.getTitulo());
        column.setSlug(request.getSlug());
        column.setResumen(request.getResumen());
        column.setContenido(request.getContenido());
        column.setImagenPrincipal(request.getImagenPrincipal());
        column.setEstado(request.getEstado());

        // Lógica de exclusión mutua para la sincronización de fechas en actualización
        column.setFechaOtorgada(request.getFechaOtorgada());
        column.setFechaPublicacion(request.getFechaOtorgada() != null ? null : request.getFechaPublicacion());

        // Comprobación de texto para asignar la fecha de publicación actual en caso de pasar a PUBLICADA sin registros previos
        if ("PUBLICADA".equalsIgnoreCase(request.getEstado()) && column.getFechaOtorgada() == null && column.getFechaPublicacion() == null) {
            column.setFechaPublicacion(LocalDateTime.now());
        }

        column = repository.save(column);
        return mapper.toResponse(column);
    }

    @Override
    public void delete(Long id) {
        ColumnArticle column = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La columna no existe."));
        repository.delete(column);
    }

    @Override
    public void increaseViews(Long id) {
        ColumnArticle column = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La columna no existe."));

        column.setVistas(column.getVistas() + 1);
        repository.save(column);
    }

    @Override
    public Map<String, String> uploadImage(MultipartFile file) {
        try {
            Map uploadResult = cloudinaryService.upload(file);
            return Map.of("url", uploadResult.get("secure_url").toString());
        } catch (IOException e) {
            throw new RuntimeException("No fue posible subir la imagen.", e);
        }
    }
}
