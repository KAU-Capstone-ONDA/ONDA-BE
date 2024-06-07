package com.capstone.onda.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    //TOKEN
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다."),

    // MEMBER
    ALREADY_EXIST_ID_EXCEPTION(HttpStatus.BAD_REQUEST, "이미 사용중인 아이디입니다."),
    ALREADY_EXIST_EMAIL_EXCEPTION(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일입니다."),
    INVALID_PASSWORD_EXCEPTION(HttpStatus.BAD_REQUEST, "비밀번호는 아이디와 같을 수 없습니다."),
    INVALID_MEMBER_EXCEPTION(HttpStatus.BAD_REQUEST, "해당하는 회원이 존재하지 않습니다."),
    INVALID_PROVIDER_TYPE_EXCEPTION(HttpStatus.BAD_REQUEST, "해당하는 Provider Type이 존재하지 않습니다."),

    // HOTEL
    INVALID_HOTEL_EXCEPTION(HttpStatus.BAD_REQUEST, "존재하지 않는 호텔입니다."),

    // ROOMTYPE
    INVALID_ROOMTYPE_EXCEPTION(HttpStatus.BAD_REQUEST, "존재하지 않는 객실타입입니다.")
    ; //Error Code를 작성한 마지막에 ;를 추가합니다.

    private final HttpStatus httpStatus;
    private final String simpleMessage;

    ErrorCode(HttpStatus httpStatus, String simpleMessage) {
        this.httpStatus = httpStatus;
        this.simpleMessage = simpleMessage;
    }
}
