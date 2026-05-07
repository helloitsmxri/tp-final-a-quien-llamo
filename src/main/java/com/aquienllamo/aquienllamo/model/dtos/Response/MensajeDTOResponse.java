package com.aquienllamo.aquienllamo.model.dtos.Response;

import com.aquienllamo.aquienllamo.model.entities.UsuarioEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MensajeDTOResponse {

    private String mensaje;
    private LocalDateTime fechaMensaje;
    private UsuarioEntity sender;
}
