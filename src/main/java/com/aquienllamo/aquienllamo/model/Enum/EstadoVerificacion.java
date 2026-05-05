package com.aquienllamo.aquienllamo.model.Enum;

public enum EstadoVerificacion {
    PENDIENTE("Pendiente"),
    APROBADO("Aprobado"),
    RECHAZADO("Rechazado");

    private String estado;

    EstadoVerificacion(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }
}
