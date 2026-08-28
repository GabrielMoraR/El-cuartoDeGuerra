package com.portalnoticias.ads.service.impl;

import com.portalnoticias.ads.dto.AdRequest;
import com.portalnoticias.ads.dto.AdResponse;
import com.portalnoticias.ads.entity.Ad;
import com.portalnoticias.ads.entity.AdLocation;
import com.portalnoticias.ads.repository.AdRepository;
import com.portalnoticias.ads.service.AdService;

import com.portalnoticias.shared.cloudinary.CloudinaryService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;

    private final CloudinaryService cloudinaryService;


    /*
    |--------------------------------------------------------------------------
    | OBTENER TODOS
    |--------------------------------------------------------------------------
    */

    @Override
    public List<AdResponse> getAll() {

        return adRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();

    }


    /*
    |--------------------------------------------------------------------------
    | OBTENER POR ID
    |--------------------------------------------------------------------------
    */

    @Override
    public AdResponse getById(Long id) {

        Ad ad = adRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Anuncio no encontrado."
                        )
                );

        return mapToResponse(ad);

    }


    /*
    |--------------------------------------------------------------------------
    | CREAR
    |--------------------------------------------------------------------------
    */

    @Override
    public AdResponse create(
            AdRequest request
    ) {

        Ad ad = new Ad();

        ad.setTitulo(request.getTitulo());

        ad.setImagen(request.getImagen());

        ad.setEnlace(request.getEnlace());

        ad.setUbicacion(
                request.getUbicacion()
        );

        ad.setActivo(
                request.getActivo() != null
                        ? request.getActivo()
                        : true
        );

        ad.setOrden(
                request.getOrden() != null
                        ? request.getOrden()
                        : 0
        );

        ad.setFechaInicio(
                request.getFechaInicio()
        );

        ad.setFechaFin(
                request.getFechaFin()
        );

        Ad savedAd =
                adRepository.save(ad);

        return mapToResponse(savedAd);

    }


    /*
    |--------------------------------------------------------------------------
    | ACTUALIZAR
    |--------------------------------------------------------------------------
    */

    @Override
    public AdResponse update(
            Long id,
            AdRequest request
    ) {

        Ad ad = adRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Anuncio no encontrado."
                        )
                );

        ad.setTitulo(request.getTitulo());

        ad.setImagen(request.getImagen());

        ad.setEnlace(request.getEnlace());

        ad.setUbicacion(
                request.getUbicacion()
        );

        ad.setActivo(
                request.getActivo()
        );

        ad.setOrden(
                request.getOrden()
        );

        ad.setFechaInicio(
                request.getFechaInicio()
        );

        ad.setFechaFin(
                request.getFechaFin()
        );

        Ad updatedAd =
                adRepository.save(ad);

        return mapToResponse(updatedAd);

    }


    /*
    |--------------------------------------------------------------------------
    | ELIMINAR
    |--------------------------------------------------------------------------
    */

    @Override
    public void delete(Long id) {

        if (!adRepository.existsById(id)) {

            throw new RuntimeException(
                    "Anuncio no encontrado."
            );

        }

        adRepository.deleteById(id);

    }


    /*
    |--------------------------------------------------------------------------
    | OBTENER ANUNCIOS ACTIVOS POR UBICACIÓN
    |--------------------------------------------------------------------------
    */

    @Override
    public List<AdResponse> getActiveByLocation(
            AdLocation ubicacion
    ) {

        return adRepository
                .findByUbicacionAndActivoTrueOrderByOrdenAsc(
                        ubicacion
                )
                .stream()
                .filter(this::isAvailable)
                .map(this::mapToResponse)
                .toList();

    }


    /*
    |--------------------------------------------------------------------------
    | SUBIR IMAGEN
    |--------------------------------------------------------------------------
    */

       /*
    |--------------------------------------------------------------------------
    | SUBIR IMAGEN
    |--------------------------------------------------------------------------
    */

    @Override
    public Map<String, String> uploadImage(
            MultipartFile file
    ) {

        try {
            Map uploadResult =
                    cloudinaryService.upload(file);

            return Map.of(
                    "url",
                    uploadResult
                            .get("secure_url")
                            .toString()
            );
        } catch (java.io.IOException e) {
            throw new RuntimeException("Error al subir la imagen a Cloudinary", e);
        }

    }



    /*
    |--------------------------------------------------------------------------
    | VALIDAR FECHAS DEL ANUNCIO
    |--------------------------------------------------------------------------
    */

    private boolean isAvailable(
            Ad ad
    ) {

        var now =
                java.time.LocalDateTime.now();

        if (
                ad.getFechaInicio() != null &&
                now.isBefore(ad.getFechaInicio())
        ) {

            return false;

        }

        if (
                ad.getFechaFin() != null &&
                now.isAfter(ad.getFechaFin())
        ) {

            return false;

        }

        return true;

    }


    /*
    |--------------------------------------------------------------------------
    | MAPEAR RESPONSE
    |--------------------------------------------------------------------------
    */

    private AdResponse mapToResponse(
            Ad ad
    ) {

        return AdResponse.builder()

                .id(ad.getId())

                .titulo(ad.getTitulo())

                .imagen(ad.getImagen())

                .enlace(ad.getEnlace())

                .ubicacion(ad.getUbicacion())

                .activo(ad.getActivo())

                .orden(ad.getOrden())

                .fechaInicio(
                        ad.getFechaInicio()
                )

                .fechaFin(
                        ad.getFechaFin()
                )

                .createdAt(
                        ad.getCreatedAt()
                )

                .updatedAt(
                        ad.getUpdatedAt()
                )

                .build();

    }

}