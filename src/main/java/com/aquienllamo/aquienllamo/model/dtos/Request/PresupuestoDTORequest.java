package com.aquienllamo.aquienllamo.model.dtos.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PresupuestoDTORequest {
    @NotBlank
    private String uuidUsuario;

    @NotNull (message = "Es necesario ingresar un precio para el presupuesto")
    private BigDecimal precioEstimado;

    @NotBlank(message = "Es necesario describir el trabajo a realizar")
    private String descripcionPresupuesto;
}
