package com.aquienllamo.aquienllamo.model.exceptions;

public class SelfReviewNotAllowedException extends RuntimeException {
    public SelfReviewNotAllowedException(String message) {
        super(message);
    }
}
