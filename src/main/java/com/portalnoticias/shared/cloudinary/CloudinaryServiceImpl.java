package com.portalnoticias.shared.cloudinary;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;


    // ============================================================
    // UPLOAD IMAGEN
    // ============================================================

    @Override
    public Map upload(MultipartFile file) throws IOException {

        return cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.emptyMap()
        );
    }


    // ============================================================
    // UPLOAD VIDEO
    // ============================================================

    @Override
    public Map uploadVideo(
            MultipartFile file
    ) throws IOException {

        return cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "resource_type", "video"
                )
        );
    }


    // ============================================================
    // DELETE IMAGEN
    // ============================================================

    @Override
    public void delete(
            String publicId
    ) throws IOException {

        cloudinary.uploader().destroy(
                publicId,
                ObjectUtils.emptyMap()
        );
    }


    // ============================================================
    // DELETE VIDEO
    // ============================================================

    @Override
    public void deleteVideo(
            String publicId
    ) throws IOException {

        cloudinary.uploader().destroy(
                publicId,
                ObjectUtils.asMap(
                        "resource_type", "video"
                )
        );
    }
}