package com.portalnoticias.columnist.mapper;

import org.springframework.stereotype.Component;

import com.portalnoticias.columnist.dto.ColumnistRequest;
import com.portalnoticias.columnist.dto.ColumnistResponse;
import com.portalnoticias.columnist.entity.Columnist;
import com.portalnoticias.user.entity.User;

@Component
public class ColumnistMapper {

    public Columnist toEntity(ColumnistRequest request, User user) {

        Columnist columnist = new Columnist();
        columnist.setCargo(request.getCargo());
        columnist.setUser(user);
        columnist.setNombre(request.getNombre());
        columnist.setCorreo(request.getCorreo());
        columnist.setBiografia(request.getBiografia());
        columnist.setFoto(request.getFoto());
        columnist.setSlug(request.getSlug());
        columnist.setFacebook(request.getFacebook());
        columnist.setInstagram(request.getInstagram());
        columnist.setTwitter(request.getTwitter());
        columnist.setActivo(true);

        return columnist;

    }

    public ColumnistResponse toResponse(Columnist columnist) {

        return ColumnistResponse.builder()

                .id(columnist.getId())

                .userId(
                        columnist.getUser() != null
                                ? columnist.getUser().getId()
                                : null
                )

                .nombre(columnist.getNombre())

                .correo(columnist.getCorreo())

                .biografia(columnist.getBiografia())

                .foto(columnist.getFoto())

                .slug(columnist.getSlug())

                .facebook(columnist.getFacebook())
                .cargo(columnist.getCargo())

                .instagram(columnist.getInstagram())

                .twitter(columnist.getTwitter())

                .activo(columnist.getActivo())

                .build();
                

    }

}