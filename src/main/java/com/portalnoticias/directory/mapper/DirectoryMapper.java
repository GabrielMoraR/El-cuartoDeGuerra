package com.portalnoticias.directory.mapper;

import org.springframework.stereotype.Component;

import com.portalnoticias.directory.dto.DirectoryRequest;
import com.portalnoticias.directory.dto.DirectoryResponse;
import com.portalnoticias.directory.entity.Directory;

@Component
public class DirectoryMapper {

    public Directory toEntity(DirectoryRequest request) {

        return Directory.builder()

                .nombre(request.getNombre())

                .cargo(request.getCargo())

                .seccion(request.getSeccion())

                .correo(request.getCorreo())

                .celular(request.getCelular())

                .foto(request.getFoto())

                .activo(
                    request.getActivo() != null
                        ? request.getActivo()
                        : true
                )

                .orden(
                    request.getOrden() != null
                        ? request.getOrden()
                        : 0
                )

                .build();
    }


    public DirectoryResponse toResponse(Directory directory) {

        return DirectoryResponse.builder()

                .id(directory.getId())

                .nombre(directory.getNombre())

                .cargo(directory.getCargo())

                .seccion(directory.getSeccion())

                .correo(directory.getCorreo())

                .celular(directory.getCelular())

                .foto(directory.getFoto())

                .activo(directory.getActivo())

                .orden(directory.getOrden())

                .createdAt(directory.getCreatedAt())

                .updatedAt(directory.getUpdatedAt())

                .build();
    }
}