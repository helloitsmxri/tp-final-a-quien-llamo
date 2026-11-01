package com.aquienllamo.aquienllamo.exception.generales;

public class InvalidPasswordEx extends RuntimeException {
    public InvalidPasswordEx(String message) {
        super(message);
    }
    public InvalidPasswordEx(){};
}
