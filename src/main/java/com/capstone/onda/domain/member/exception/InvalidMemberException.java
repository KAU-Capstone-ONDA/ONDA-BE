package com.capstone.onda.domain.member.exception;

import com.capstone.onda.global.exception.ApplicationException;
import com.capstone.onda.global.exception.ErrorCode;

public class InvalidMemberException extends ApplicationException {

    public InvalidMemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
