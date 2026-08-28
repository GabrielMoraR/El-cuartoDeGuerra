package com.portalnoticias.autores.service;

import com.portalnoticias.autores.dto.request.AuthorRequest;
import com.portalnoticias.autores.dto.response.AuthorResponse;
import java.util.List;

public interface AuthorService {
    List<AuthorResponse> findAll();
    AuthorResponse findById(Long id);
    AuthorResponse save(AuthorRequest request);
    AuthorResponse update(Long id, AuthorRequest request);
    void deleteById(Long id);
}
