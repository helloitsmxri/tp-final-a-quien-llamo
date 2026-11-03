package com.aquienllamo.aquienllamo.entidades.presupuesto.dto.response;


import com.aquienllamo.aquienllamo.entidades.presupuesto.enums.EstadoPresupuestoE;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PresupuestoDTOResponse {

    private String uuid; // para el front!!

    // Datos del emisor/receptor
    // TÉCNICO:
    private String nombreTecnico;
    private String apellidoTecnico;

    // USUARIO:
    private String nombreUsuario;
    private String apellidoUsuario;

    // proyecto
    private String nombreProyecto;
    private String observaciones;
    private List<PresupuestoDetalleDTOResponse> detalles; // esto sería x ej materiales

    // subtotales y descuentos
    private BigDecimal subtotal;
    private BigDecimal descuentoPorcentaje;
    private BigDecimal montoDescuento;

    // TOTAL
    private BigDecimal precioEstimado;

    private LocalDateTime fechaRealizado;
    private EstadoPresupuestoE estado;
}
