package com.aquienllamo.aquienllamo.entidades.ubicacion.exceptions;


public class UbicacionNotFoundEx extends RuntimeException {
    public UbicacionNotFoundEx(String message) {
        super("Ubicacion no encontrada");
    }

    public UbicacionNotFoundEx() {
    }
}
