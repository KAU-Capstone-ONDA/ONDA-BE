package com.capstone.onda.global.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class OndaException extends RuntimeException {
    public final Map<String, String> validation = new HashMap<>();

    public OndaException(String message) {
        super(message);
    }

    public OndaException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
