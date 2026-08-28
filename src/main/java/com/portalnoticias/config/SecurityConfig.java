package com.portalnoticias.config;

import org.springframework.http.HttpMethod;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.Customizer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import com.portalnoticias.auth.security.JwtAuthenticationConverter;
import jakarta.servlet.http.HttpServletResponse; 

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    private JwtAuthenticationConverter jwtAuthenticationConverter;

    // ============================================================
    // PASSWORD ENCODER
    // ============================================================
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ============================================================
    // JWT DECODER
    // ============================================================
    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKey key = new SecretKeySpec(
                jwtSecret.getBytes(),
                "HmacSHA256"
        );
        return NimbusJwtDecoder
                .withSecretKey(key)
                .macAlgorithm(MacAlgorithm.HS256) 
                .build();
    }
    // ============================================================
    // SECURITY FILTER CHAIN (FILTRADO ABSOLUTO POR VERBOS HTTP)
    // ============================================================
    // ============================================================
// SECURITY FILTER CHAIN (FILTRADO ABSOLUTO POR VERBOS HTTP)
// ============================================================
@Bean
public SecurityFilterChain securityFilterChain(
        HttpSecurity http
) throws Exception {

    http
        .cors(Customizer.withDefaults()) 
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            // 1. Permite solicitudes preflight de CORS sin token
            .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() 
            
            // 2. ENDPOINT DE AUTENTICACIÓN PÚBLICO (Para que puedan iniciar sesión)
            .requestMatchers("/api/auth/**").permitAll()

            // 3. REGLA DE ORO DE NOTICIAS: Cualquier consulta GET en el sistema es 100% pública
            .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
            
            // 4. EXCEPCIÓN PÚBLICA DE ESCRITURA (Para registrar las reproducciones de videos desde la web)
            .requestMatchers(HttpMethod.POST, "/api/video-notas/*/view").permitAll()
            
            // 🌟 4.5 EXCEPCIÓN PÚBLICA PARA EL CONTADOR DE VISTAS DE COLUMNAS (Añade esta línea)
            .requestMatchers(HttpMethod.PATCH, "/api/public/columns/*/view").permitAll()
            
            // 5. BLINDAJE TOTAL: Cualquier POST, PUT o DELETE requerirá un token JWT válido
            .anyRequest().authenticated()
        )
        .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint((request, response, authException) -> {
                    System.err.println("🛑 ERROR DE AUTENTICACIÓN JWT: " + authException.getMessage());
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
                })
        )
        .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter))
        );

    return http.build();
}

}
