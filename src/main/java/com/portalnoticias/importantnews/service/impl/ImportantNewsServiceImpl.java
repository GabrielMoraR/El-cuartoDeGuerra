package com.portalnoticias.importantnews.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.portalnoticias.importantnews.dto.*;
import com.portalnoticias.importantnews.entity.ImportantNews;
import com.portalnoticias.importantnews.mapper.ImportantNewsMapper;
import com.portalnoticias.importantnews.repository.ImportantNewsRepository;
import com.portalnoticias.importantnews.service.ImportantNewsService;
import com.portalnoticias.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImportantNewsServiceImpl implements ImportantNewsService {

    private final ImportantNewsRepository repository;
    private final ImportantNewsMapper mapper;

    @Override
    public List<ImportantNewsResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<ImportantNewsResponse> findActive() {
        return repository
                .findByActivoTrueOrderByOrdenAsc()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public ImportantNewsResponse findById(Long id) {
        ImportantNews news = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe la noticia importante."
                ));
        return mapper.toResponse(news);
    }

    @Override
    public ImportantNewsResponse save(ImportantNewsRequest request) {
        ImportantNews news = mapper.toEntity(request);
        return mapper.toResponse(repository.save(news));
    }

    @Override
    public ImportantNewsResponse update(Long id, ImportantNewsRequest request) {
        ImportantNews news = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe la noticia importante."
                ));

        news.setTitulo(request.getTitulo());
        news.setMensaje(request.getMensaje());
        news.setTipo(request.getTipo());
        news.setActivo(request.getActivo());
        news.setOrden(request.getOrden());
        news.setTiempoMostrar(request.getTiempoMostrar());
        news.setFechaInicio(request.getFechaInicio());
        news.setFechaFin(request.getFechaFin());

        return mapper.toResponse(repository.save(news));
    }

    @Override
    public void delete(Long id) {
        ImportantNews news = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No existe la noticia importante."
                ));
        repository.delete(news);
    }
}
