package com.capstone.onda.global.security.exception;

import com.capstone.onda.global.exception.ApplicationException;
import com.capstone.onda.global.exception.ErrorCode;

public class AccessDeniedException extends ApplicationException {

    public AccessDeniedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
