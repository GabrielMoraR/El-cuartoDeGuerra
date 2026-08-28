package com.portalnoticias.column.entity;

import java.time.LocalDateTime;
import com.portalnoticias.columnist.entity.Columnist;
import com.portalnoticias.shared.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "columnas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColumnArticle extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "columnista_id")
    private Columnist columnist;

    @Column(nullable = false, length = 500)
    private String titulo;

    @Column(nullable = false, unique = true, length = 500)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String resumen;

    @Column(columnDefinition = "LONGTEXT")
    private String contenido;

    @Column(name = "imagen_principal", length = 500)
    private String imagenPrincipal;

    @Column(nullable = false)
    private Long vistas;

    @Column(nullable = false, columnDefinition = "ENUM('BORRADOR', 'PUBLICADA', 'ARCHIVADA')")
    private String estado;

    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion;

    // NUEVO CAMPO: Fecha otorgada manualmente de forma opcional
    @Column(name = "fecha_otorgada")
    private LocalDateTime fechaOtorgada;

    /**
     * Método utilitario (Helper) para obtener la fecha activa real.
     * Si el editor otorgó una fecha manual, se usa esa; si no, se usa la de publicación.
     */
    @Transient
    public LocalDateTime getFechaEfectiva() {
        return this.fechaOtorgada != null ? this.fechaOtorgada : this.fechaPublicacion;
    }
}
