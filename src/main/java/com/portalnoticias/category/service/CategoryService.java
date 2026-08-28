package com.portalnoticias.category.service;

import com.portalnoticias.category.dto.CategoryRequest;
import com.portalnoticias.category.dto.CategoryResponse;
import com.portalnoticias.shared.pagination.PageResponse;

public interface CategoryService {

    PageResponse<CategoryResponse> findAll(
            int page,
            int size,
            String sortBy,
            String direction
    );

    CategoryResponse findById(Long id);

    CategoryResponse save(CategoryRequest request);

    CategoryResponse update(Long id, CategoryRequest request);

    void delete(Long id);

}