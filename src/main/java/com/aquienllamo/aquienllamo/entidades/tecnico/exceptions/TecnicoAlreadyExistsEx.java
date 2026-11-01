package com.aquienllamo.aquienllamo.entidades.tecnico.exceptions;

public class TecnicoAlreadyExistsEx extends RuntimeException {
    public TecnicoAlreadyExistsEx(String message) {
        super(message);
    }
    public TecnicoAlreadyExistsEx() {}
}
