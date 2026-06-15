package com.aquienllamo.aquienllamo.model.dtos.Request;

import com.aquienllamo.aquienllamo.model.Enum.TipoCaracteristicaE;
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

    @NotNull
    private TipoCaracteristicaE tipo;
}
