package com.aquienllamo.aquienllamo.model.dtos.Response;

import com.aquienllamo.aquienllamo.model.Enum.Estado;
import com.aquienllamo.aquienllamo.model.Enum.MetodoDePago;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PagoDTOResponse {

    private String uuid; //id del pago generado

    private String uuidTrabajo; //id del trabajo que se abono

    private MetodoDePago metodoDePago;

    private LocalDateTime fechaPago;

    private Estado estadoPago;

}
