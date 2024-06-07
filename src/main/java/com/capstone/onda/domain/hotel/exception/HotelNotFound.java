package com.capstone.onda.domain.hotel.exception;


import com.capstone.onda.global.exception.ApplicationException;
import com.capstone.onda.global.exception.ErrorCode;


public class HotelNotFound extends ApplicationException {

    public HotelNotFound(ErrorCode errorCode) { super(errorCode); }
}
