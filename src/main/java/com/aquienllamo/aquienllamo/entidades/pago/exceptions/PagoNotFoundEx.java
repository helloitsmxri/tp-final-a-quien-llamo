package com.aquienllamo.aquienllamo.entidades.pago.exceptions;

public class PagoNotFoundEx extends RuntimeException {
    public PagoNotFoundEx(String message) {
        super(message);
    }
}
