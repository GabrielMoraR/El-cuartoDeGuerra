package com.portalnoticias.autores.service.impl;

import com.portalnoticias.autores.dto.request.AuthorRequest;
import com.portalnoticias.autores.dto.response.AuthorResponse;
import com.portalnoticias.autores.entity.Author;
import com.portalnoticias.autores.mapper.AuthorMapper;
import com.portalnoticias.autores.repository.AuthorRepository;
import com.portalnoticias.autores.service.AuthorService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public List<AuthorResponse> findAll() {
        return authorMapper.toResponseList(authorRepository.findAll());
    }

    @Override
    public AuthorResponse findById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor no encontrado con ID: " + id));
        return authorMapper.toResponse(author);
    }

    @Override
    public AuthorResponse save(AuthorRequest request) {
        Author author = authorMapper.toEntity(request);
        return authorMapper.toResponse(authorRepository.save(author));
    }

    @Override
    public AuthorResponse update(Long id, AuthorRequest request) {
        Author authorExistente = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor no encontrado para actualizar"));
        
        authorMapper.updateEntityFromRequest(request, authorExistente);
        return authorMapper.toResponse(authorRepository.save(authorExistente));
    }

    @Override
    public void deleteById(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new RuntimeException("El autor no existe");
        }
        authorRepository.deleteById(id);
    }
}
