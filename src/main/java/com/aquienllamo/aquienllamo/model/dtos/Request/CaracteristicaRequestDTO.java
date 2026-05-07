package com.aquienllamo.aquienllamo.model.dtos.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CaracteristicaRequestDTO {

    @NotBlank(message = "Se debe seleccionar al menos un adjetivo!")
    private String valorAdjetivo;
    
}
