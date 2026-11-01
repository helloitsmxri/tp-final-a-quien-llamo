package com.aquienllamo.aquienllamo.entidades.caracteristica.dtos.request;

import com.aquienllamo.aquienllamo.entidades.caracteristica.enums.TipoCaracteristicaE;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CaracteristicaDTORequest {

    @NotBlank(message = "Se debe seleccionar al menos un adjetivo!")
    private String valorAdjetivo;

    @NotNull(message = "Se debe seleccionar un tipo de valor para el adjetivo!")
    private TipoCaracteristicaE tipo;
}
