package com.aquienllamo.aquienllamo.entidades.usuario.exceptions;

public class UserAlreadyDisabledEx extends RuntimeException {
    public UserAlreadyDisabledEx() {
    }

    public UserAlreadyDisabledEx(String message) {
        super(message);
    }

    public UserAlreadyDisabledEx(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyDisabledEx(Throwable cause) {
        super(cause);
    }
}
