package com.aquienllamo.aquienllamo.entidades.presupuesto.dto.request;

import com.aquienllamo.aquienllamo.entidades.presupuesto.dto.response.PresupuestoDetalleDTOResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PresupuestoDTORequest {
    @NotBlank(message = "El nombre del proyecto es obligatorio")
    @Size(max = 120, message = "El nombre no puede superar los 120 caracteres")
    private String nombreProyecto;

    @Size(max = 2000, message = "Las observaciones no pueden superar los 2000 caracteres")
    private String observaciones;

    @NotNull(message = "Es necesario ingresar el descuento")
    @DecimalMin(value = "0.00", message = "El descuento no puede ser negativo")
    @DecimalMax(value = "100.00", message = "El descuento no puede superar el 100%")
    @Digits(integer = 3, fraction = 2, message = "El porcentaje no tiene un formato válido")
    private BigDecimal descuentoPorcentaje;

    @NotEmpty(message = "El presupuesto debe tener al menos un concepto")
    @Valid
    private List<PresupuestoDetalleDTORequest> detalles;
}
