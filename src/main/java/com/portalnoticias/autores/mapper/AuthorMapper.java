package com.portalnoticias.autores.mapper;

import com.portalnoticias.autores.dto.request.AuthorRequest;
import com.portalnoticias.autores.dto.response.AuthorResponse;
import com.portalnoticias.autores.entity.Author;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthorMapper {
    
    public AuthorResponse toResponse(Author author) {
        if (author == null) return null;
        AuthorResponse response = new AuthorResponse();
        response.setId(author.getId());
        response.setNombre(author.getNombre());
        return response;
    }
    
    public Author toEntity(AuthorRequest request) {
        if (request == null) return null;
        Author author = new Author();
        author.setNombre(request.getNombre());
        return author;
    }
    
    public List<AuthorResponse> toResponseList(List<Author> authors) {
        return authors.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void updateEntityFromRequest(AuthorRequest request, Author author) {
        if (request != null && author != null) {
            author.setNombre(request.getNombre());
        }
    }
}
