package com.portalnoticias.ads.repository;

import com.portalnoticias.ads.entity.Ad;
import com.portalnoticias.ads.entity.AdLocation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdRepository
        extends JpaRepository<Ad, Long> {

    List<Ad> findByUbicacionAndActivoTrueOrderByOrdenAsc(
        AdLocation ubicacion
    );

}