package com.aquienllamo.aquienllamo.entidades.trabajo.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EstadoTrabajo {
    Pendiente,
    Finalizado,
    Cancelado,
    @JsonProperty("En proceso")
    En_proceso;

}
