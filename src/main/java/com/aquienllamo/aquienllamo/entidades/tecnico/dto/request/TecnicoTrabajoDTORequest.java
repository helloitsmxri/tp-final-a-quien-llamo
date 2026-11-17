package com.aquienllamo.aquienllamo.entidades.tecnico.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TecnicoTrabajoDTORequest {
    @Size(max = 22, message = "El CVU no puede superar los 22 caracteres.")
    private String cvu;

    @Size(max = 100, message = "El alias no puede superar los 100 caracteres.")
    private String alias;
}
