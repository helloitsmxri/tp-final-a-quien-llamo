package com.aquienllamo.aquienllamo.entidades.trabajo.exceptions;

public class CompletedWorkRequiredEx extends RuntimeException {
    public CompletedWorkRequiredEx(String message) {
        super(message);
    }
}
