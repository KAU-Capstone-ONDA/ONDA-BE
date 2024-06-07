package com.capstone.onda.domain.member.entity;

import com.capstone.onda.domain.member.exception.InvalidProviderTypeException;
import com.capstone.onda.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum ProviderType {
    KAKAO("KAKAO"),
    NAVER("NAVER");

    private final String value;

    ProviderType(String value){
        this.value = value;
    }

    public static ProviderType toProviderType(String loginProviderType) {
        for (ProviderType providerType : values()) {
            if (providerType.value.equalsIgnoreCase(loginProviderType)) {
                return providerType;
            }
        }
        throw new InvalidProviderTypeException(ErrorCode.INVALID_PROVIDER_TYPE_EXCEPTION);
    }
}

