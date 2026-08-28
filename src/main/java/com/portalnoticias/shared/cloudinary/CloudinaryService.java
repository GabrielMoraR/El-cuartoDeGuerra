package com.portalnoticias.shared.cloudinary;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {

    Map upload(MultipartFile file) throws IOException;

    Map uploadVideo(MultipartFile file) throws IOException;

    void delete(String publicId) throws IOException;

    void deleteVideo(String publicId) throws IOException;
}