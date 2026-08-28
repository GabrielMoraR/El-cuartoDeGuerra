package com.portalnoticias.category.mapper;

import org.springframework.stereotype.Component;

import com.portalnoticias.category.dto.CategoryRequest;
import com.portalnoticias.category.dto.CategoryResponse;
import com.portalnoticias.category.entity.Category;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(Category category){

        return CategoryResponse.builder()

                .id(category.getId())
                .nombre(category.getNombre())
                .slug(category.getSlug())
                .descripcion(category.getDescripcion())
                .color(category.getColor())
                .icono(category.getIcono())
                .activo(category.getActivo())

                .build();

    }

    public Category toEntity(CategoryRequest request){

        return Category.builder()

                .nombre(request.getNombre())
                .slug(request.getSlug())
                .descripcion(request.getDescripcion())
                .color(request.getColor())
                .icono(request.getIcono())

                .build();

    }

}