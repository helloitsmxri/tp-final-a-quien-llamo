package com.aquienllamo.aquienllamo.model.exceptions;

public class UserNotFoundEx extends RuntimeException {
    public UserNotFoundEx(String message) {
        super(message);
    }
    public UserNotFoundEx(){}
}
