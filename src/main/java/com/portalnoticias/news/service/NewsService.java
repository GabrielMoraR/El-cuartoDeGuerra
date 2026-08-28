package com.portalnoticias.news.service;

import java.util.Map;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.portalnoticias.news.dto.NewsRequest;
import com.portalnoticias.news.dto.NewsResponse;
import com.portalnoticias.shared.pagination.PageResponse;

public interface NewsService {

PageResponse<NewsResponse> findAll(
int page,
int size,
String sortBy,
String direction
);

NewsResponse findById(Long id);

NewsResponse save(NewsRequest request);

NewsResponse update(Long id, NewsRequest request);

Map<String, String> uploadImage(MultipartFile file);

List<NewsResponse> getLatestNews();

void delete(Long id);

List<NewsResponse> getWeeklyHighlights();

void increaseViews(Long id);

NewsResponse findBySlug(String slug);
 List<NewsResponse> searchPublicNews(String query);
}