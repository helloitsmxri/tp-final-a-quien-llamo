package com.aquienllamo.aquienllamo.model.exceptions;

public class CompletedWorkRequiredEx extends RuntimeException {
    public CompletedWorkRequiredEx(String message) {
        super(message);
    }
}
