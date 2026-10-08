package com.portalnoticias.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();

        // ============================================================
        // ORÍGENES PERMITIDOS
        // ============================================================
        config.setAllowedOrigins(Arrays.asList(
            "http://localhost:5173"
        ));

        // ============================================================
        // MÉTODOS HTTP
        // ============================================================
        config.setAllowedMethods(Arrays.asList(
            "GET",
            "POST",
            "PUT",
            "DELETE",
            "PATCH",
            "OPTIONS"
        ));

        // ============================================================
        // HEADERS
        // ============================================================
        config.setAllowedHeaders(Arrays.asList(
            "Authorization",
            "Content-Type",
            "X-Requested-With",
            "Accept",
            "Origin"
        ));

        // ============================================================
        // CREDENTIALS
        // ============================================================
        config.setAllowCredentials(true);

        // ============================================================
        // HEADERS EXPUESTOS
        // ============================================================
        config.setExposedHeaders(Arrays.asList(
            "Authorization"
        ));

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
