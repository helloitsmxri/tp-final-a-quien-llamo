package com.aquienllamo.aquienllamo.model.dtos.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class PresupuestoDTORequest {
    @NotNull
    private Integer idTecnico;

    @NotNull
    private BigDecimal precioEstimado;

    @NotBlank
    private String descripcionPresupuesto;
}
