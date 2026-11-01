package com.aquienllamo.aquienllamo.entidades.usuario.exceptions;

public class UserNotFoundEx extends RuntimeException {
    public UserNotFoundEx(String message) {
        super(message);
    }
    public UserNotFoundEx(){}
}
