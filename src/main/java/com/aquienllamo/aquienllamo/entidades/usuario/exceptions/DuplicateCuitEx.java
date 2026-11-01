package com.aquienllamo.aquienllamo.entidades.usuario.exceptions;

public class DuplicateCuitEx extends RuntimeException {
    public DuplicateCuitEx(String message) {
        super(message);
    }
    public DuplicateCuitEx() {
    }
}
