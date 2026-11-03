package com.aquienllamo.aquienllamo.entidades.presupuesto.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PresupuestoDetalleDTOResponse {
    private String descripcion;
    private BigDecimal precioUnitario;
    private Integer cantidad;
    private BigDecimal total;
}
