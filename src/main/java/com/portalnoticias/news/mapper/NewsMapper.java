package com.portalnoticias.news.mapper;

import org.springframework.stereotype.Component;
import com.portalnoticias.category.entity.Category;
import com.portalnoticias.autores.entity.Author; // 🌟 Importamos tu entidad Author
import com.portalnoticias.autores.dto.response.AuthorResponse; // 🌟 Importamos tu respuesta de Autor
import com.portalnoticias.news.dto.NewsRequest;
import com.portalnoticias.news.dto.NewsResponse;
import com.portalnoticias.news.entity.News;

@Component
public class NewsMapper {

    // 🌟 AHORA RECIBE TAMBIÉN EL OBJETO AUTHOR PROCESADO POR EL SERVICE
    public News toEntity(NewsRequest request, Category category, Author author) {
        News news = new News();

        news.setCategory(category);
        news.setAuthor(author); // 🌟 Seteamos la entidad Author directo en la noticia

        news.setTitulo(request.getTitulo());
        news.setSlug(request.getSlug());
        news.setResumen(request.getResumen());
        news.setContenido(request.getContenido());
        news.setImagenPortada(request.getImagenPortada());
        news.setImagenPrincipal(request.getImagenPrincipal());

        news.setDestacada(
                request.getDestacada() != null
                        ? request.getDestacada()
                        : false
        );

        news.setPublicada(
                request.getPublicada() != null
                        ? request.getPublicada()
                        : false
        );

        news.setSeoTitle(request.getSeoTitle());
        news.setSeoDescription(request.getSeoDescription());
        news.setFechaPublicacion(request.getFechaPublicacion());
        news.setVistas(0);

        return news;
    }

    public NewsResponse toResponse(News news) {
        // 🌟 MAPEO MANUAL DEL AUTHORRESPONSE PARA EL BUILDER
        AuthorResponse authorRes = null;
        if (news.getAuthor() != null) {
            authorRes = new AuthorResponse();
            authorRes.setId(news.getAuthor().getId());
            authorRes.setNombre(news.getAuthor().getNombre());
        }

        return NewsResponse.builder()
                .id(news.getId())
                .categoryId(news.getCategory().getId())
                .categoryName(news.getCategory().getNombre())
                .categoryColor(news.getCategory().getColor())
                .categoryIcon(news.getCategory().getIcono())

                .author(authorRes) // 🌟 Enviamos el objeto AuthorResponse completo estructurado

                .titulo(news.getTitulo())
                .slug(news.getSlug())
                .resumen(news.getResumen())
                .contenido(news.getContenido())
                .imagenPrincipal(news.getImagenPrincipal())
                .destacada(news.getDestacada())
                .publicada(news.getPublicada())
                .vistas(news.getVistas())
                .seoTitle(news.getSeoTitle())
                .seoDescription(news.getSeoDescription())
                .fechaPublicacion(news.getFechaPublicacion())
                .imagenPortada(news.getImagenPortada())
                .build();
    }
}
