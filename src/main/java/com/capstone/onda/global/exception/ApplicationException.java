package com.capstone.onda.global.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

    private final ErrorCode errorCode;

    public ApplicationException(ErrorCode errorCode) {
        super(errorCode.getSimpleMessage());
        this.errorCode = errorCode;
    }
}
