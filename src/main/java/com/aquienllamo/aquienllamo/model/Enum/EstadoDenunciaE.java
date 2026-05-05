package com.aquienllamo.aquienllamo.model.Enum;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_VALUES)
public enum EstadoDenunciaE {

    @JsonProperty("Comprobado")
    COMPROBADO,

    @JsonProperty("En proceso")
    EN_PROCESO,

    @JsonProperty("Pendiente")
    PENDIENTE;
}
