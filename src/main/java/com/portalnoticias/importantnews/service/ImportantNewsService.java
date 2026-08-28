package com.portalnoticias.importantnews.service;

import java.util.List;

import com.portalnoticias.importantnews.dto.*;

public interface ImportantNewsService {

    List<ImportantNewsResponse> findAll();

    List<ImportantNewsResponse> findActive();

    ImportantNewsResponse findById(Long id);

    ImportantNewsResponse save(ImportantNewsRequest request);

    ImportantNewsResponse update(Long id, ImportantNewsRequest request);

    void delete(Long id);
}
