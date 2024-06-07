package com.capstone.onda.domain.roomType.exception;


import com.capstone.onda.global.exception.ApplicationException;
import com.capstone.onda.global.exception.ErrorCode;

public class RoomTypeNotFound extends ApplicationException {
    public RoomTypeNotFound(ErrorCode errorCode) {
        super(errorCode);
    }
}
