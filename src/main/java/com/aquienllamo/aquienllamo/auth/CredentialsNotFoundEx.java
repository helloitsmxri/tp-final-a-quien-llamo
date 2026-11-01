package com.aquienllamo.aquienllamo.auth;

public class CredentialsNotFoundEx extends RuntimeException {
    public CredentialsNotFoundEx(String message) {
        super(message);
    }
}
