package com.portalnoticias.ads.entity;

import com.portalnoticias.shared.entity.BaseEntity;
import com.portalnoticias.ads.entity.AdLocation;
import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ad extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
    |--------------------------------------------------------------------------
    | Nombre interno del anuncio
    |--------------------------------------------------------------------------
    */

    @Column(nullable = false, length = 200)
    private String titulo;

    /*
    |--------------------------------------------------------------------------
    | URL de la imagen
    |--------------------------------------------------------------------------
    */

    @Column(nullable = false, length = 1000)
    private String imagen;

    /*
    |--------------------------------------------------------------------------
    | Enlace al que dirigirá el anuncio
    |--------------------------------------------------------------------------
    */

    @Column(length = 1000)
    private String enlace;

    /*
    |--------------------------------------------------------------------------
    | Lugar donde aparecerá el anuncio
    |--------------------------------------------------------------------------
    */
@Enumerated(EnumType.STRING)
@Column(nullable = false, length = 100)
private AdLocation ubicacion;

    /*
    |--------------------------------------------------------------------------
    | Control de visibilidad
    |--------------------------------------------------------------------------
    */

    @Builder.Default
    private Boolean activo = true;

    /*
    |--------------------------------------------------------------------------
    | Orden dentro de la misma ubicación
    |--------------------------------------------------------------------------
    */

    @Builder.Default
    private Integer orden = 0;

    /*
    |--------------------------------------------------------------------------
    | Programación opcional
    |--------------------------------------------------------------------------
    */

    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;

}