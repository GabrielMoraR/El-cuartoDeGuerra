package com.portalnoticias.auth.security;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.portalnoticias.user.entity.User;
import com.portalnoticias.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository
                .findByUsernameOrEmail(username, username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Usuario o correo no encontrado."
                        )
                );

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(
                        user.getRole()
                                .getPermissions()
                                .stream()
                                .map(permission ->
                                        new SimpleGrantedAuthority(
                                                permission.getCode()
                                        )
                                )
                                .collect(Collectors.toSet())
                )
                .accountLocked(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .disabled(!Boolean.TRUE.equals(user.getActive()))
                .build();
    }
}