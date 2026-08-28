package com.portalnoticias.videonota.entity;

import com.portalnoticias.category.entity.Category;
import com.portalnoticias.shared.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "video_notas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoNota extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(length = 500)
    private String descripcion;

    @Column(name = "video_url", nullable = false, length = 500)
    private String videoUrl;

    @Column(name = "video_public_id", nullable = false, length = 255)
    private String videoPublicId;

    @Column(name = "thumbnail_url", length = 500)
    private String thumbnailUrl;

    @Column(nullable = false)
    @Builder.Default
    private Long vistas = 0L;

        // CORRECCIÓN: Cambiado de LAZY a EAGER para que la categoría viaje completa en las consultas
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    @org.hibernate.annotations.NotFound(
        action = org.hibernate.annotations.NotFoundAction.IGNORE
    )
    private Category categoria;

    @Column(nullable = false)
    @Builder.Default
    private Boolean activo = true;
}