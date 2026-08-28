package com.portalnoticias.videonota.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portalnoticias.videonota.entity.VideoNota;

public interface VideoNotaRepository
        extends JpaRepository<VideoNota, Long> {

    List<VideoNota> findByActivoTrueOrderByCreatedAtDesc();

    List<VideoNota> findByCategoriaIdAndActivoTrueOrderByCreatedAtDesc(
            Long categoriaId
    );
}