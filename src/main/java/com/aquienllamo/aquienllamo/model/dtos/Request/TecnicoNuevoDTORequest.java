package com.aquienllamo.aquienllamo.model.dtos.Request;

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
