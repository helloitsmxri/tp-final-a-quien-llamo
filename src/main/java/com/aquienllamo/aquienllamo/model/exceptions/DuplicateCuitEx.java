package com.aquienllamo.aquienllamo.model.exceptions;

public class DuplicateCuitEx extends RuntimeException {
    public DuplicateCuitEx(String message) {
        super(message);
    }
    public DuplicateCuitEx() {
    }
}
