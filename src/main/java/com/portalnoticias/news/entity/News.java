package com.portalnoticias.news.entity;

import java.time.LocalDateTime;

import com.portalnoticias.category.entity.Category;
import com.portalnoticias.autores.entity.Author; // 🌟 IMPORTAMOS TU NUEVA ENTIDAD AUTHOR
import com.portalnoticias.shared.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "noticias")
public class News extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Category category;

    // 🌟 REEMPLAZO: Removimos TipoAutoria e inyectamos la relación directa con Authors
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = true) // Permite null por si alguna noticia no lleva autor asignado
    private Author author;

    @Column(nullable = false, length = 250)
    private String titulo;

    @Column(nullable = false, unique = true, length = 300)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String resumen;

    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String contenido;

    @Column(name = "imagen_principal", length = 500)
    private String imagenPrincipal;

    @Column(nullable = false)
    private Boolean destacada = false;

    @Column(nullable = false)
    private Boolean publicada = false;

    @Column(nullable = false)
    private Integer vistas = 0;

    @Column(name = "seo_title")
    private String seoTitle;

    @Column(name = "seo_description", length = 500)
    private String seoDescription;

    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion;

    @Column(name = "imagen_portada", length = 500)
    private String imagenPortada;
}
