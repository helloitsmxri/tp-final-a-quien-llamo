package com.aquienllamo.aquienllamo.model.Enum;

public enum MetodoDePago {
    TRANSFERENCIA("Transferencia"),
    EFECTIVO("Efectivo"),
    DEBITO("Debito"),
    CREDITO("Credito");

    private String metodo;

    MetodoDePago(String metodo) {
        this.metodo = metodo;
    }

    public String getMetodo() {
        return metodo;
    }
}
