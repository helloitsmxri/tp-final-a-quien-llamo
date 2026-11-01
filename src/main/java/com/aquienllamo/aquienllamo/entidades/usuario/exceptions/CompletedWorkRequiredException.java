package com.aquienllamo.aquienllamo.model.exceptions;

public class CompletedWorkRequiredException extends RuntimeException {
    public CompletedWorkRequiredException(String message) {
        super(message);
    }
}
