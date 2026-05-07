package com.aquienllamo.aquienllamo.model.dtos.Request;

import com.aquienllamo.aquienllamo.model.Enum.MetodoDePago;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PagoDTORequest {

    @NotBlank(message = "Debe ingresar el trabajo que va a pagar.")
    private String uuidTrabajo;

    @NotNull(message = "Debe seleccionar un metodo de pago")
    private MetodoDePago metodoDePago;


}
