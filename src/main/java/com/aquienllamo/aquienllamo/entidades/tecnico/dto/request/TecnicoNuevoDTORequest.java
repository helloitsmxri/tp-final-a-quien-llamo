package com.aquienllamo.aquienllamo.entidades.tecnico.dto.request;

import com.aquienllamo.aquienllamo.entidades.usuario.dtos.request.UsuarioDTORequest;
import jakarta.validation.Valid;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TecnicoNuevoDTORequest {

    @Valid // para validar campos internos
    private UsuarioDTORequest usuario;
    @Valid
    private TecnicoDTORequest tecnico;
}
