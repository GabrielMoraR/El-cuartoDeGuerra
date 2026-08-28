package com.portalnoticias.videonota.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.portalnoticias.category.entity.Category;
import com.portalnoticias.category.repository.CategoryRepository;
import com.portalnoticias.shared.cloudinary.CloudinaryService;
import com.portalnoticias.shared.exception.ResourceNotFoundException;
import com.portalnoticias.videonota.dto.VideoNotaRequest;
import com.portalnoticias.videonota.dto.VideoNotaResponse;
import com.portalnoticias.videonota.entity.VideoNota;
import com.portalnoticias.videonota.mapper.VideoNotaMapper;
import com.portalnoticias.videonota.repository.VideoNotaRepository;
import com.portalnoticias.videonota.service.VideoNotaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class VideoNotaServiceImpl
        implements VideoNotaService {

    private final VideoNotaRepository repository;

    private final VideoNotaMapper mapper;

    private final CloudinaryService cloudinaryService;

    private final CategoryRepository categoryRepository;


    // ============================================================
    // LISTAR TODOS
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public List<VideoNotaResponse> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }


    // ============================================================
    // LISTAR ACTIVOS
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public List<VideoNotaResponse> findAllActive() {

        return repository
                .findByActivoTrueOrderByCreatedAtDesc()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }


    // ============================================================
    // LISTAR POR CATEGORÍA
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public List<VideoNotaResponse> findByCategoria(
            Long categoriaId
    ) {

        return repository
                .findByCategoriaIdAndActivoTrueOrderByCreatedAtDesc(
                        categoriaId
                )
                .stream()
                .map(mapper::toResponse)
                .toList();
    }


    // ============================================================
    // BUSCAR POR ID
    // ============================================================

    @Override
    @Transactional(readOnly = true)
    public VideoNotaResponse findById(Long id) {

        VideoNota videoNota =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "La VideoNota no existe."
                                )
                        );

        return mapper.toResponse(videoNota);
    }
    // ============================================================
    // CREAR
    // ============================================================

    @Override
    public VideoNotaResponse save(
            VideoNotaRequest request,
            MultipartFile video
    ) {

        validateVideo(video);

        Category categoria =
                categoryRepository.findById(
                        request.getCategoriaId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "La categoría no existe."
                        )
                );

        VideoNota videoNota =
                mapper.toEntity(
                        request,
                        categoria
                );

        try {

            Map<?, ?> uploadResult =
                    cloudinaryService.uploadVideo(video);

            String videoUrl =
                    uploadResult
                            .get("secure_url")
                            .toString();

            String videoPublicId =
                    uploadResult
                            .get("public_id")
                            .toString();


            // Generación de miniatura nativa de Cloudinary (.jpg)
            String thumbnailUrl = 
                    videoUrl.substring(0, videoUrl.lastIndexOf(".")) + ".jpg";


            videoNota.setVideoUrl(videoUrl);

            videoNota.setVideoPublicId(
                    videoPublicId
            );

            videoNota.setThumbnailUrl(
                    thumbnailUrl
            );

            videoNota.setActivo(true);

            videoNota =
                    repository.save(videoNota);

            return mapper.toResponse(videoNota);

        } catch (IOException e) {

            throw new RuntimeException(
                    "No fue posible subir el video.",
                    e
            );
        }
    }


    // ============================================================
    // ACTUALIZAR INFORMACIÓN
    // ============================================================

    @Override
    public VideoNotaResponse update(
            Long id,
            VideoNotaRequest request
    ) {

        VideoNota videoNota =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "La VideoNota no existe."
                                )
                        );

        Category categoria =
                categoryRepository.findById(
                        request.getCategoriaId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "La categoría no existe."
                        )
                );

        videoNota.setTitulo(
                request.getTitulo()
        );

        videoNota.setDescripcion(
                request.getDescripcion()
        );

        videoNota.setCategoria(
                categoria
        );

        videoNota =
                repository.save(videoNota);

        return mapper.toResponse(videoNota);
    }


    // ============================================================
    // REEMPLAZAR VIDEO
    // ============================================================

    @Override
    public VideoNotaResponse updateVideo(
            Long id,
            MultipartFile video
    ) {

        validateVideo(video);


        VideoNota videoNota =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "La VideoNota no existe."
                                )
                        );


        String oldPublicId =
                videoNota.getVideoPublicId();


        try {

            // ----------------------------------------------------
            // SUBIR NUEVO VIDEO
            // ----------------------------------------------------

            Map<?, ?> uploadResult =
                    cloudinaryService.uploadVideo(video);


            String newVideoUrl =
                    uploadResult
                            .get("secure_url")
                            .toString();


            String newPublicId =
                    uploadResult
                            .get("public_id")
                            .toString();


            // ----------------------------------------------------
            // NUEVO THUMBNAIL (Modificado para tu Cloudinary)
            // ----------------------------------------------------

            String newThumbnailUrl =
                    newVideoUrl.substring(0, newVideoUrl.lastIndexOf(".")) + ".jpg";


            // ----------------------------------------------------
            // ACTUALIZAR
            // ----------------------------------------------------

            videoNota.setVideoUrl(
                    newVideoUrl
            );

            videoNota.setVideoPublicId(
                    newPublicId
            );

            videoNota.setThumbnailUrl(
                    newThumbnailUrl
            );


            videoNota =
                    repository.save(videoNota);


            // ----------------------------------------------------
            // ELIMINAR VIDEO ANTERIOR
            // ----------------------------------------------------

            if (
                    oldPublicId != null
                    && !oldPublicId.isBlank()
            ) {

                cloudinaryService.deleteVideo(
                        oldPublicId
                );
            }


            return mapper.toResponse(videoNota);


        } catch (IOException e) {

            throw new RuntimeException(
                    "No fue posible reemplazar el video.",
                    e
            );
        }
    }


    // ============================================================
    // INCREMENTAR VISTAS
    // ============================================================

    @Override
    public void incrementViews(Long id) {

        VideoNota videoNota =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "La VideoNota no existe."
                                )
                        );


        Long vistas =
                videoNota.getVistas() == null
                        ? 0L
                        : videoNota.getVistas();


        videoNota.setVistas(
                vistas + 1
        );


        repository.save(videoNota);
    }


    // ============================================================
    // ELIMINAR
    // ============================================================

    @Override
    public void delete(Long id) {

        VideoNota videoNota =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "La VideoNota no existe."
                                )
                        );


        if (
                videoNota.getVideoPublicId() != null
                && !videoNota.getVideoPublicId().isBlank()
        ) {

            try {

                cloudinaryService.deleteVideo(
                        videoNota.getVideoPublicId()
                );

            } catch (IOException e) {

                throw new RuntimeException(
                        "No fue posible eliminar el video de Cloudinary.",
                        e
                );
            }
        }


        repository.delete(videoNota);
    }


    // ============================================================
    // VALIDAR VIDEO
    // ============================================================

    private void validateVideo(
            MultipartFile video
    ) {

        if (
                video == null
                || video.isEmpty()
        ) {

            throw new IllegalArgumentException(
                    "El video es obligatorio."
            );
        }


        String contentType =
                video.getContentType();


        if (
                contentType == null
                || !contentType.startsWith("video/")
        ) {

            throw new IllegalArgumentException(
                    "El archivo debe ser un video."
            );
        }
    }

}
