package com.capstone.onda.domain.member.exception;

import com.capstone.onda.global.exception.ApplicationException;
import com.capstone.onda.global.exception.ErrorCode;

public class InvalidProviderTypeException extends ApplicationException {

    public InvalidProviderTypeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
