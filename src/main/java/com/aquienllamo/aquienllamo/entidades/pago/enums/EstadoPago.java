package com.aquienllamo.aquienllamo.entidades.pago.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_VALUES)

public enum EstadoPago {
    Confirmado,
    Rechazado,
    Cancelado,
    @JsonProperty("Pendiente de revisión")
    Pendiente_de_revision;

}
