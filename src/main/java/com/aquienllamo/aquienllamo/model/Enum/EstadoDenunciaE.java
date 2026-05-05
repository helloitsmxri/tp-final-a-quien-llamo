package com.aquienllamo.aquienllamo.model.Enum;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_VALUES)
public enum EstadoDenunciaE {
    Pendiente,
    Finalizado,
    Cancelado,
    @JsonProperty("En proceso")
    En_proceso;
}
