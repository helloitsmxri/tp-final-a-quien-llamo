package com.aquienllamo.aquienllamo.model.exceptions;

public class UserAlreadySuspendedEx extends RuntimeException {
    public UserAlreadySuspendedEx() {
    }

    public UserAlreadySuspendedEx(String message) {
        super(message);
    }

    public UserAlreadySuspendedEx(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadySuspendedEx(Throwable cause) {
        super(cause);
    }
}
