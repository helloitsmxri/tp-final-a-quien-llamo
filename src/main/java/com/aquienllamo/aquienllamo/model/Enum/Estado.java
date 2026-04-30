package com.aquienllamo.aquienllamo.model.Enum;

public enum Estado {
    CONFIRMADO("Confirmado"),
    RECHAZADO("Rechazado"),
    PENDIENTE("Pendiente de revisión");

    private String estado;

    Estado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }
}
