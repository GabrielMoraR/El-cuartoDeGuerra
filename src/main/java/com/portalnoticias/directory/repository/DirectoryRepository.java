package com.portalnoticias.directory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portalnoticias.directory.entity.Directory;

public interface DirectoryRepository
        extends JpaRepository<Directory, Long> {

    List<Directory> findByActivoTrueOrderByOrdenAsc();

}