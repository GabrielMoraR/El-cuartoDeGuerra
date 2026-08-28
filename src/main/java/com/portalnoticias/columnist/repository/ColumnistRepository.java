package com.portalnoticias.columnist.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.portalnoticias.columnist.entity.Columnist;

public interface ColumnistRepository extends JpaRepository<Columnist, Long> {

    boolean existsBySlug(String slug);

    boolean existsByCorreo(String correo);
boolean existsByUserId(Long userId);
    Optional<Columnist> findBySlug(String slug);

    List<Columnist> findByNombreContainingIgnoreCase(String nombre);
    Optional<Columnist> findByCorreo(String correo);
    List<Columnist> findByActivoTrue();

    Page<Columnist> findByActivoTrue(Pageable pageable);

}