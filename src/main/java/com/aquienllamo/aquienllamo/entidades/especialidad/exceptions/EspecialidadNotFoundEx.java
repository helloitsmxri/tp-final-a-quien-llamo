package com.aquienllamo.aquienllamo.entidades.especialidad.exceptions;

public class EspecialidadNotFoundEx extends RuntimeException {
    public EspecialidadNotFoundEx(String message) {
        super(message);
    }
    public EspecialidadNotFoundEx() {}
}
