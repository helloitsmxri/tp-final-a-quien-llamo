package com.aquienllamo.aquienllamo.model.mappers;

import com.aquienllamo.aquienllamo.model.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor //crea automaticamente el constructor
public class RatingMapper {

    private final UsuarioRepository usuarioRepository;


}
