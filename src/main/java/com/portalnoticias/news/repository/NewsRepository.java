package com.portalnoticias.news.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.portalnoticias.news.entity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    boolean existsBySlug(String slug);

    Optional<News> findBySlug(String slug);

    List<News> findByPublicadaTrue();

    List<News> findByDestacadaTrue();

    List<News> findByCategoryId(Long categoryId);

    //  REEMPLAZADO: Ahora buscamos noticias por el ID del autor
    List<News> findByAuthorId(Long authorId);

    List<News> findTop5ByPublicadaTrueOrderByFechaPublicacionDesc();

    List<News> findTop10ByDestacadaTrueAndPublicadaTrue();

    List<News> findTop10ByCategoryIdAndPublicadaTrueOrderByFechaPublicacionDesc(Long categoryId);

    //  REEMPLAZADO: Obtener las últimas 10 noticias publicadas de un autor específico
    List<News> findTop10ByAuthorIdAndPublicadaTrueOrderByFechaPublicacionDesc(Long authorId);

    List<News> findByTituloContainingIgnoreCaseOrResumenContainingIgnoreCase(String titulo, String resumen);

    @Query("""
    SELECT n
    FROM News n
    WHERE n.publicada = true
    AND n.category.id = :categoryId
    AND n.fechaPublicacion >= :fechaInicio
    ORDER BY n.vistas DESC
    """)
    List<News> findWeeklyHighlightsByCategory(
    @Param("categoryId") Long categoryId,
    @Param("fechaInicio") LocalDateTime fechaInicio
    );

    // Metodo para minuto a minuto
    @Query("""
    SELECT n
    FROM News n
    WHERE n.publicada = true
    ORDER BY n.fechaPublicacion DESC
    """)
    List<News> findLatestNews(Pageable pageable);

    //  OPTIMIZADO: Agregamos también un FETCH a 'author' para evitar el problema de consultas N+1 al listar noticias
    @Query("SELECT n FROM News n LEFT JOIN FETCH n.category LEFT JOIN FETCH n.author ORDER BY n.createdAt DESC")
    Page<News> findAllWithCategory(Pageable pageable);
}
