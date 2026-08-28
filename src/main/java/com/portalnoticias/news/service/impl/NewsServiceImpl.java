package com.portalnoticias.news.service.impl;

import org.springframework.data.domain.*;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.portalnoticias.category.entity.Category;
import com.portalnoticias.category.repository.CategoryRepository;
import com.portalnoticias.autores.entity.Author; // 🌟 IMPORTACIÓN DE LA NUEVA ENTIDAD AUTHOR
import com.portalnoticias.autores.repository.AuthorRepository; // 🌟 IMPORTACIÓN DEL NUEVO REPOSITORIO DE AUTORES
import com.portalnoticias.news.dto.NewsRequest;
import com.portalnoticias.news.dto.NewsResponse;
import com.portalnoticias.news.entity.News;
import com.portalnoticias.news.mapper.NewsMapper;
import com.portalnoticias.news.repository.NewsRepository;
import com.portalnoticias.news.service.NewsService;
import com.portalnoticias.shared.cloudinary.CloudinaryService;
import com.portalnoticias.shared.exception.ConflictException;
import com.portalnoticias.shared.exception.ResourceNotFoundException;
import com.portalnoticias.shared.pagination.PageMapper;
import com.portalnoticias.shared.pagination.PageResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final CloudinaryService cloudinaryService;
    private final NewsRepository repository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository; //  INYECTADO AUTOMÁTICAMENTE POR LOMBOK
    private final NewsMapper mapper;

    @Override
    public Map<String, String> uploadImage(MultipartFile file) {
        try {
            Map uploadResult = cloudinaryService.upload(file);
            return Map.of(
                "url",
                uploadResult.get("secure_url").toString()
            );
        } catch (IOException e) {
            throw new RuntimeException("No fue posible subir la imagen.", e);
        }
    }

    @Override
    public List<NewsResponse> getWeeklyHighlights() {
        LocalDateTime fechaInicio = LocalDateTime.now().minusDays(7);
        List<Category> categories = categoryRepository.findAll();
        List<NewsResponse> response = new ArrayList<>();
        for (Category category : categories) {
            List<News> news = repository.findWeeklyHighlightsByCategory(
                    category.getId(),
                    fechaInicio
            );

            if (!news.isEmpty()) {
                response.add(
                        mapper.toResponse(news.get(0))
                );
            }
        }
        return response;
    }

    @Override
    public PageResponse<NewsResponse> findAll(
            int page,
            int size,
            String sortBy,
            String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<News> newsPage = repository.findAll(pageable);
        Page<NewsResponse> response = newsPage.map(mapper::toResponse);

        return PageMapper.from(response);
    }

    @Override
    public NewsResponse findById(Long id) {
        News news = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La noticia no existe."));
        return mapper.toResponse(news);
    }

    @Override
    public NewsResponse save(NewsRequest request) {
        if (repository.existsBySlug(request.getSlug())) {
            throw new ConflictException("Ya existe una noticia con ese slug.");
        }
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("La categoria no existe."));

        //  VALIDACIÓN DEL AUTOR SELECCIONADO
        Author author = null;
        if (request.getAuthorId() != null) {
            author = authorRepository.findById(request.getAuthorId())
                    .orElseThrow(() -> new ResourceNotFoundException("El autor seleccionado no existe."));
        }

        //  ACTUALIZADO: Pasamos el objeto 'author' al mapper
        News news = mapper.toEntity(request, category, author);

        if (news.getVistas() == null) {
            news.setVistas(0);
        }
        if (news.getDestacada() == null) {
            news.setDestacada(false);
        }
        if (news.getPublicada() == null) {
            news.setPublicada(false);
        }

        news = repository.save(news);
        return mapper.toResponse(news);
    }

    @Override
    public NewsResponse update(Long id, NewsRequest request) {
        News news = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La noticia no existe."));
        repository.findBySlug(request.getSlug())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new ConflictException("Ya existe una noticia con ese slug.");
                    }
                });

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("La categoria no existe."));

        //  VALIDACIÓN Y ACTUALIZACIÓN DEL AUTOR EN EL UPDATE
        Author author = null;
        if (request.getAuthorId() != null) {
            author = authorRepository.findById(request.getAuthorId())
                    .orElseThrow(() -> new ResourceNotFoundException("El autor seleccionado no existe."));
        }

        news.setCategory(category);
        news.setAuthor(author); //  REEMPLAZADO: Seteamos el nuevo Author en lugar de tipoAutoria
        news.setTitulo(request.getTitulo());
        news.setSlug(request.getSlug());
        news.setResumen(request.getResumen());
        news.setContenido(request.getContenido());
        news.setImagenPrincipal(request.getImagenPrincipal());
        news.setImagenPortada(request.getImagenPortada());
        news.setDestacada(request.getDestacada());
        news.setPublicada(request.getPublicada());
        news.setSeoTitle(request.getSeoTitle());
        news.setSeoDescription(request.getSeoDescription());

        if (Boolean.TRUE.equals(news.getPublicada()) && news.getFechaPublicacion() == null) {
            news.setFechaPublicacion(LocalDateTime.now());
        }

        news = repository.save(news);
        return mapper.toResponse(news);
    }

    @Override
    public void delete(Long id) {
        News news = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La noticia no existe."));
        repository.delete(news);
    }

    @Override
    public void increaseViews(Long id) {
        News news = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La noticia no existe."));
        news.setVistas(news.getVistas() + 1);
        repository.save(news);
    }

    @Override
    public NewsResponse findBySlug(String slug) {
        News news = repository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("La noticia no existe."));
        return mapper.toResponse(news);
    }

    @Override
    public List<NewsResponse> getLatestNews() {
        Pageable pageable = PageRequest.of(0, 5);
        return repository.findLatestNews(pageable)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<NewsResponse> searchPublicNews(String query) {
        if (query == null || query.trim().isEmpty()) {
            return List.of();
        }

        List<News> noticias = repository
                .findByTituloContainingIgnoreCaseOrResumenContainingIgnoreCase(query.trim(), query.trim());

        return noticias.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
