package com.portalnoticias.column.service;

import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

import com.portalnoticias.column.dto.ColumnRequest;
import com.portalnoticias.column.dto.ColumnResponse;

public interface ColumnService {

    /**
     * Obtiene las últimas columnas que tengan el estado "PUBLICADA".
     * Ahora utiliza el ordenamiento inteligente priorizando la fecha otorgada.
     */
    List<ColumnResponse> getLatestColumns();

    List<ColumnResponse> findAll();

    ColumnResponse findById(Long id);

    ColumnResponse findBySlug(String slug);

    ColumnResponse save(ColumnRequest request);

    ColumnResponse update(Long id, ColumnRequest request);

    void delete(Long id);

    void increaseViews(Long id);
    
    Map<String, String> uploadImage(MultipartFile file);
}
