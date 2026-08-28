package com.portalnoticias.column.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.portalnoticias.column.entity.ColumnArticle;

public interface ColumnRepository extends JpaRepository<ColumnArticle, Long> {

    Optional<ColumnArticle> findBySlug(String slug);

    boolean existsBySlug(String slug);

    /**
     * Obtiene las columnas por estado ordenadas de forma descendente.
     * COALESCE evalúa los argumentos en orden: si 'fechaOtorgada' NO es nula, ordena por ella.
     * Si 'fechaOtorgada' ES nula, pasa al segundo argumento y ordena por 'fechaPublicacion'.
     */
    @Query("SELECT c FROM ColumnArticle c WHERE c.estado = :estado " +
           "ORDER BY COALESCE(c.fechaOtorgada, c.fechaPublicacion) DESC")
    List<ColumnArticle> findByEstadoOrderByFechaEfectivaDesc(
        @Param("estado") String estado, 
        Pageable pageable
    );
}
