package com.aquienllamo.aquienllamo.model.exceptions;

public class UserFoundEx extends RuntimeException {
    public UserFoundEx() {
    }

    public UserFoundEx(String message) {
        super(message);
    }

    public UserFoundEx(String message, Throwable cause) {
        super(message, cause);
    }

    public UserFoundEx(Throwable cause) {
        super(cause);
    }
}
