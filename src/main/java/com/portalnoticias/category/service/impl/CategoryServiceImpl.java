package com.portalnoticias.category.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.portalnoticias.category.dto.CategoryRequest;
import com.portalnoticias.category.dto.CategoryResponse;
import com.portalnoticias.category.entity.Category;
import com.portalnoticias.category.mapper.CategoryMapper;
import com.portalnoticias.category.repository.CategoryRepository;
import com.portalnoticias.category.service.CategoryService;
import com.portalnoticias.shared.exception.ConflictException;
import com.portalnoticias.shared.exception.ResourceNotFoundException;
import com.portalnoticias.shared.pagination.PageMapper;
import com.portalnoticias.shared.pagination.PageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    private final CategoryMapper mapper;

    @Override
    public PageResponse<CategoryResponse> findAll(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Category> result = repository.findAll(pageable);

        Page<CategoryResponse> response = result.map(mapper::toResponse);

        return PageMapper.from(response);

    }

    @Override
    public CategoryResponse findById(Long id) {

        Category category = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("La categoría no existe."));

        return mapper.toResponse(category);

    }

    @Override
    public CategoryResponse save(CategoryRequest request) {

        if (repository.existsByNombre(request.getNombre())) {

            throw new ConflictException(
                    "Ya existe una categoría con ese nombre."
            );

        }

        if (repository.existsBySlug(request.getSlug())) {

            throw new ConflictException(
                    "Ya existe una categoría con ese slug."
            );

        }

        Category category = mapper.toEntity(request);

        category.setActivo(true);

        category = repository.save(category);

        return mapper.toResponse(category);

    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest request) {

        Category category = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("La categoría no existe."));

        repository.findByNombre(request.getNombre())
                .ifPresent(existing -> {

                    if (!existing.getId().equals(id)) {

                        throw new ConflictException(
                                "Ya existe una categoría con ese nombre."
                        );

                    }

                });

        repository.findBySlug(request.getSlug())
                .ifPresent(existing -> {

                    if (!existing.getId().equals(id)) {

                        throw new ConflictException(
                                "Ya existe una categoría con ese slug."
                        );

                    }

                });

        category.setNombre(request.getNombre());
        category.setSlug(request.getSlug());
        category.setDescripcion(request.getDescripcion());
        category.setColor(request.getColor());
        category.setIcono(request.getIcono());

        category = repository.save(category);

        return mapper.toResponse(category);

    }

    @Override
    public void delete(Long id) {

        Category category = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("La categoría no existe."));

        repository.delete(category);

    }

}