package com.portalnoticias.ads.controller;

import com.portalnoticias.ads.dto.AdRequest;
import com.portalnoticias.ads.dto.AdResponse;
import com.portalnoticias.ads.entity.AdLocation;
import com.portalnoticias.ads.service.AdService;
import com.portalnoticias.shared.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize; // Importación indispensable para interceptar las peticiones
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ads")
@RequiredArgsConstructor
public class AdController {

    private final AdService adService;


    /*
    |--------------------------------------------------------------------------
    | OBTENER TODOS
    |--------------------------------------------------------------------------
    */

    @GetMapping
    @PreAuthorize("hasAuthority('ADS_VIEW')") // Restringido al panel administrativo para auditar publicidad
    public ApiResponse<List<AdResponse>> getAll() {

        return new ApiResponse<>(
                true,
                "Anuncios obtenidos correctamente.",
                adService.getAll()
        );
    }


    /*
    |--------------------------------------------------------------------------
    | OBTENER POR ID
    |--------------------------------------------------------------------------
    */

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADS_VIEW')") // Protegido para la consulta individual de gestión
    public ApiResponse<AdResponse> getById(
            @PathVariable Long id
    ) {

        return new ApiResponse<>(
                true,
                "Anuncio obtenido correctamente.",
                adService.getById(id)
        );
    }


    /*
    |--------------------------------------------------------------------------
    | CREAR
    |--------------------------------------------------------------------------
    */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADS_CREATE')") // Solo Administradores y Publicistas pueden inyectar pauta publicitaria
    public ApiResponse<AdResponse> create(
            @Valid
            @RequestBody
            AdRequest request
    ) {

        return new ApiResponse<>(
                true,
                "Anuncio creado correctamente.",
                adService.create(request)
        );
    }
    /*
    |--------------------------------------------------------------------------
    | ACTUALIZAR
    |--------------------------------------------------------------------------
    */

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADS_EDIT')") // Protegido contra modificaciones no autorizadas de banners
    public ApiResponse<AdResponse> update(
            @PathVariable Long id,
            @Valid
            @RequestBody
            AdRequest request
    ) {

        return new ApiResponse<>(
                true,
                "Anuncio actualizado correctamente.",
                adService.update(
                        id,
                        request
                )
        );
    }


    /*
    |--------------------------------------------------------------------------
    | ELIMINAR
    |--------------------------------------------------------------------------
    */

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADS_DELETE')") // Control total de remoción física de pauta
    public ApiResponse<Void> delete(
            @PathVariable Long id
    ) {

        adService.delete(id);

        return new ApiResponse<>(
                true,
                "Anuncio eliminado correctamente.",
                null
        );
    }


    /*
    |--------------------------------------------------------------------------
    | SUBIR IMAGEN
    |--------------------------------------------------------------------------
    */

    @PostMapping("/upload-image")
    @PreAuthorize("hasAuthority('ADS_CREATE') or hasAuthority('ADS_EDIT')") // Permite la carga multimedia a roles autorizados
    public ApiResponse<Map<String, String>> uploadImage(
            @RequestParam("file")
            MultipartFile file
    ) {

        return new ApiResponse<>(
                true,
                "Imagen subida correctamente.",
                adService.uploadImage(file)
        );
    }


    /*
    |--------------------------------------------------------------------------
    | OBTENER POR UBICACIÓN (PÚBLICO - Usado en el portal de noticias)
    |--------------------------------------------------------------------------
    */

    @GetMapping("/location/{ubicacion}")
    // Queda completamente libre para que las tarjetas publicitarias carguen en el feed público
    public ApiResponse<List<AdResponse>> getByLocation(
            @PathVariable
            AdLocation ubicacion
    ) {

        return new ApiResponse<>(
                true,
                "Anuncios obtenidos correctamente.",
                adService.getActiveByLocation(
                                ubicacion
                        )
        );
    }
}
