package com.portalnoticias.videonota.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.portalnoticias.videonota.dto.VideoNotaRequest;
import com.portalnoticias.videonota.dto.VideoNotaResponse;

public interface VideoNotaService {

    List<VideoNotaResponse> findAll();

    List<VideoNotaResponse> findAllActive();

    List<VideoNotaResponse> findByCategoria(Long categoriaId);

    VideoNotaResponse findById(Long id);

    VideoNotaResponse save(
            VideoNotaRequest request,
            MultipartFile video
    );

    VideoNotaResponse update(
            Long id,
            VideoNotaRequest request
    );

    VideoNotaResponse updateVideo(
            Long id,
            MultipartFile video
    );

    void incrementViews(Long id);

    void delete(Long id);
}