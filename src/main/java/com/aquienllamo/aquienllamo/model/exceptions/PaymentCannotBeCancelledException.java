package com.aquienllamo.aquienllamo.model.exceptions;

public class PaymentCannotBeCancelledException extends RuntimeException {
    public PaymentCannotBeCancelledException(String message) {
        super(message);
    }
}
