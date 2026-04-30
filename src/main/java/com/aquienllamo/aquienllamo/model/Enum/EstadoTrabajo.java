package com.aquienllamo.aquienllamo.model.Enum;

public enum EstadoTrabajo {
    PENDIENTE("Pendiente"),
    FINALIZADO("Finalizado"),
    CANCELADO("Cancelado"),
    ENPROCESO("En proceso");

    private String estadoTrabajo;

    EstadoTrabajo(String estadoTrabajo) {
        this.estadoTrabajo = estadoTrabajo;
    }

    public String getEstadoTrabajo() {
        return estadoTrabajo;
    }
}
