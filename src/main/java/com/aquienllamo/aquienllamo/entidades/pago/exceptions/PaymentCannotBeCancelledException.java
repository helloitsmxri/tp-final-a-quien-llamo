package com.aquienllamo.aquienllamo.entidades.pago.exceptions;

public class PaymentCannotBeCancelledException extends RuntimeException {
    public PaymentCannotBeCancelledException(String message) {
        super(message);
    }
}
