package com.portalnoticias.columnist.service;
import com.portalnoticias.columnist.dto.ColumnistRequest;
import com.portalnoticias.columnist.dto.ColumnistResponse;
import com.portalnoticias.shared.pagination.PageResponse;
import org.springframework.web.multipart.MultipartFile;
public interface ColumnistService {

    PageResponse<ColumnistResponse> findAll(
            int page,
            int size,
            String sortBy,
            String direction
    );

    ColumnistResponse findById(Long id);

    ColumnistResponse save(ColumnistRequest request);

    ColumnistResponse update(Long id, ColumnistRequest request);

    void delete(Long id);
    // mservicio de imagenes
    ColumnistResponse uploadPhoto(Long id, MultipartFile file);

void deletePhoto(Long id);

}