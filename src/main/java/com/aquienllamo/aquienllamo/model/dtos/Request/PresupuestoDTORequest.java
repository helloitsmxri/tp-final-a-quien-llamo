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
    @NotNull
    private Integer idTecnico;

    @NotNull
    private BigDecimal precioEstimado;

    @NotBlank
    private String descripcionPresupuesto;
}
