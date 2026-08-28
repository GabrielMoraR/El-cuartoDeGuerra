package com.portalnoticias.category.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portalnoticias.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsByNombre(String nombre);

    boolean existsBySlug(String slug);

    Optional<Category> findByNombre(String nombre);

    Optional<Category> findBySlug(String slug);

    List<Category> findByActivoTrue();

}