package com.aquienllamo.aquienllamo.model.exceptions;

public class TecnicoAlreadyExistsEx extends RuntimeException {
    public TecnicoAlreadyExistsEx(String message) {
        super(message);
    }
    public TecnicoAlreadyExistsEx() {}
}
