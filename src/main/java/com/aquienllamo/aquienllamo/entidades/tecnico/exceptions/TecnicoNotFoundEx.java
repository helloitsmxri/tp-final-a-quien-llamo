package com.aquienllamo.aquienllamo.entidades.tecnico.exceptions;

public class TecnicoNotFoundEx extends RuntimeException {
    public TecnicoNotFoundEx(String message) {
        super(message);
    }

    public TecnicoNotFoundEx() {
    }

}
