package com.capstone.onda.domain.member.exception;

import com.capstone.onda.global.exception.ApplicationException;
import com.capstone.onda.global.exception.ErrorCode;

public class InvalidHotelException extends ApplicationException {

    public InvalidHotelException(ErrorCode errorCode) {
        super(errorCode);
    }
}