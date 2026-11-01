package com.aquienllamo.aquienllamo.entidades.especialidad.exceptions;

public class EspecialidadAlreadyExistsEx extends RuntimeException {
    public EspecialidadAlreadyExistsEx(String message) {
        super(message);
    }
    public EspecialidadAlreadyExistsEx() {}
}
