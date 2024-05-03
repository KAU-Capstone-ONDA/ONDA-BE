package com.capstone.onda.domain.member.exception;

import com.capstone.onda.global.exception.ApplicationException;
import com.capstone.onda.global.exception.ErrorCode;

public class InvalidTokenException extends ApplicationException {

    public InvalidTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
