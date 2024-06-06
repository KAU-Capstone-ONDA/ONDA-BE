package com.capstone.onda.domain.roomType.enumeration.service;


import com.capstone.onda.domain.roomType.EnumManager;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ServiceOption implements EnumManager {
    COOKING("취사 가능"),
    WIFI("WIFI"),
    PARKING("주차가능"),
    PET("반려동물 동반"),
    SHUTTLE_BUS("셔틀버스"),
    BREAKFAST("조식서비스"),
    PRINTER("프린터 사용"),
    VALET_PARKING("발렛 파킹"),
    LUGGAGE_STORAGE("짐 보관"),
    PICKUP("픽업"),
    NO_SMOKING("금연"),
    FREE_PARKING("무료주차"),
    DISALBED_CONVENIENCE("장애인편의시설"),
    AIRPORT_SHUTTLE("공항 셔틀"),
    BAR("바/라운지"),
    PERSONAL_LOCKER("개인 사물함"),
    MASSAGE("마사지"),
    EVENT("프로포즈/파티/이벤트"),
    HOUSEHOLD_MEDICINE("상비약"),
    BIKE_RENTAL("자전거 대여"),
    CAMPFIRE("캠프 파이어"),
    BOARD_GAME("보드게임"),
    MOVIE("영화관람"),
    SERVICE("서비스");





    private final String viewName;

    ServiceOption(String viewName) {
        this.viewName = viewName;
    }
    public static ServiceOption fromCode(String dbData) {
        return Arrays.stream(ServiceOption.values())
                .filter(v -> v.getViewName().equals(dbData))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("서비스 옵션에 %s가 존재하지 않습니다.", dbData)));
    }


    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return viewName;
    }
}
