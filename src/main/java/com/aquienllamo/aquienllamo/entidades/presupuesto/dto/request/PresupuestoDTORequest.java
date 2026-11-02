package com.aquienllamo.aquienllamo.entidades.presupuesto.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PresupuestoDTORequest {

    @NotNull (message = "Es necesario ingresar un precio para el presupuesto")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a cero")
    private BigDecimal precioEstimado;

    @NotBlank(message = "Es necesario describir el trabajo a realizar")
    private String descripcionPresupuesto;
}
