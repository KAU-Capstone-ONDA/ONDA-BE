package com.capstone.onda.domain.member.exception;

import com.capstone.onda.global.exception.ApplicationException;
import com.capstone.onda.global.exception.ErrorCode;

public class AlreadyExistEmailException extends ApplicationException {

    public AlreadyExistEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
