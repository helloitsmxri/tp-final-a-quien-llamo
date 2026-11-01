package com.aquienllamo.aquienllamo.entidades.habilidad.exceptions;

public class HabilidadNotFoundEx extends RuntimeException {
    public HabilidadNotFoundEx(String message) {
        super(message);
    }
    public HabilidadNotFoundEx() {}
}
