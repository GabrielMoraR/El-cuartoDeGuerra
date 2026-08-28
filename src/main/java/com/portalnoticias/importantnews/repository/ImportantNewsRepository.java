package com.portalnoticias.importantnews.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portalnoticias.importantnews.entity.ImportantNews;

public interface ImportantNewsRepository extends JpaRepository<ImportantNews, Long> {

    List<ImportantNews> findByActivoTrueOrderByOrdenAsc();
}
