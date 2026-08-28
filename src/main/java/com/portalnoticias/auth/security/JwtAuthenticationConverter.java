package com.portalnoticias.auth.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationConverter
        implements Converter<Jwt, AbstractAuthenticationToken> {

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        
        // Lista final donde unificaremos Roles y Permisos
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        // 1. EXTRAER EL ROL (Ej: "ADMIN", "EDITOR")
        String role = jwt.getClaimAsString("role"); // Ajusta a "roles" si viene en plural en tu JWT
        if (role != null && !role.isEmpty()) {
            // Le añadimos el prefijo ROLE_ requerido por Spring Security de forma automática
            if (!role.startsWith("ROLE_")) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
            } else {
                authorities.add(new SimpleGrantedAuthority(role.toUpperCase()));
            }
        }

        // 2. EXTRAER LOS PERMISOS (Tu lógica original)
        List<String> permissions = jwt.getClaimAsStringList("permissions");
        if (permissions != null) {
            permissions.stream()
                    .map(SimpleGrantedAuthority::new)
                    .forEach(authorities::add);
        }

        // Retornamos el token con la combinación de Roles y Permisos listos para ser evaluados
        return new JwtAuthenticationToken(
                jwt,
                authorities,
                jwt.getSubject()
        );
    }
}
