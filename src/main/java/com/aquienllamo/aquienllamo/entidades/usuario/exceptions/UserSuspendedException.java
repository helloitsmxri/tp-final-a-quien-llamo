package com.aquienllamo.aquienllamo.entidades.usuario.exceptions;

public class UserSuspendedException extends RuntimeException {
    public UserSuspendedException(String message) {
        super(message);
    }
}
