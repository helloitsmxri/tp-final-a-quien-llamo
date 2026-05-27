package com.aquienllamo.aquienllamo.model.exceptions;

public class PortfolioAlreadyExistsEx extends RuntimeException {
    public PortfolioAlreadyExistsEx(String message) {
        super(message);
    }
}
