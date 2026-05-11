package com.aquienllamo.aquienllamo.model.exceptions;


public class UbicacionNotFoundEx extends RuntimeException {
    public UbicacionNotFoundEx(String message) {
        super("Ubicacion no encontrada");
    }

    public UbicacionNotFoundEx() {
    }
}
