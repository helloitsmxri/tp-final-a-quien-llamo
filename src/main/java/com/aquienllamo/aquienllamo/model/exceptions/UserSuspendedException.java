package com.aquienllamo.aquienllamo.model.exceptions;

public class UserSuspendedException extends RuntimeException {
    public UserSuspendedException(String message) {
        super(message);
    }
}
