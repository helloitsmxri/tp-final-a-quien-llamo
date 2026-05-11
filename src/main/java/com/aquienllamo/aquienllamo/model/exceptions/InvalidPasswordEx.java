package com.aquienllamo.aquienllamo.model.exceptions;

public class InvalidPasswordEx extends RuntimeException {
    public InvalidPasswordEx(String message) {
        super(message);
    }
    public InvalidPasswordEx(){};
}
