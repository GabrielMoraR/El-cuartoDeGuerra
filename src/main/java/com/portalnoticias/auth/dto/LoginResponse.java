package com.portalnoticias.auth.dto;
import java.util.Set;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String token;

    private String tokenType;

    private Long userId;

    private String username;

    private String name;
    private String email;


    private String role;
    private Set<String> permissions;

}