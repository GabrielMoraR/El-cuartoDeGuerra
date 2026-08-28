package com.portalnoticias.directory.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.portalnoticias.directory.dto.DirectoryRequest;
import com.portalnoticias.directory.dto.DirectoryResponse;

public interface DirectoryService {

    List<DirectoryResponse> findAll();

    List<DirectoryResponse> findActive();

    DirectoryResponse findById(Long id);
   

    DirectoryResponse save(DirectoryRequest request);

    DirectoryResponse update(
            Long id,
            DirectoryRequest request
    );

    void delete(Long id);

    DirectoryResponse uploadPhoto(
            Long id,
            MultipartFile file
    );
}