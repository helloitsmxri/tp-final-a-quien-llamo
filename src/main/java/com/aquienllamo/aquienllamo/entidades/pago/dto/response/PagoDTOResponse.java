package com.aquienllamo.aquienllamo.entidades.pago.dto.response;

import com.aquienllamo.aquienllamo.entidades.pago.enums.EProveedorPago;
import com.aquienllamo.aquienllamo.entidades.pago.enums.EstadoPago;
import com.aquienllamo.aquienllamo.entidades.pago.enums.MetodoDePago;
import lombok.*;

import java.time.LocalDateTime;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PagoDTOResponse {

    private String uuid;

    private String uuidTrabajo;

    private String nombreProyecto;

    private String descripcionProyecto;

    private EProveedorPago proveedorDePago;

    private MetodoDePago metodoDePago;

    private LocalDateTime fechaPago;

    private EstadoPago estadoPago;

    private String checkoutUrl;

    private String externalPaymentId;

    private String estadoExterno;
}
