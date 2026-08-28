package com.portalnoticias.ads.service;

import com.portalnoticias.ads.dto.AdRequest;
import com.portalnoticias.ads.dto.AdResponse;
import com.portalnoticias.ads.entity.AdLocation;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface AdService {

    List<AdResponse> getAll();

    AdResponse getById(Long id);

    AdResponse create(AdRequest request);

    AdResponse update(
        Long id,
        AdRequest request
    );

    void delete(Long id);

    List<AdResponse> getActiveByLocation(
        AdLocation ubicacion
    );

    Map<String, String> uploadImage(
        MultipartFile file
    );

}