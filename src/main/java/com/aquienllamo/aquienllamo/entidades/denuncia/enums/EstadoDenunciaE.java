package com.aquienllamo.aquienllamo.entidades.denuncia.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_VALUES)
public enum EstadoDenunciaE {
    Pendiente,
    Aprobada,
    Rechazada,
    @JsonProperty("En proceso")
    En_proceso;
}
