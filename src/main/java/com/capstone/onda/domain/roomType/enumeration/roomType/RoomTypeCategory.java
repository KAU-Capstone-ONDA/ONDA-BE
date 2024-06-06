package com.capstone.onda.domain.roomType.enumeration.roomType;


import com.capstone.onda.domain.roomType.EnumManager;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum RoomTypeCategory implements EnumManager {
    DOUBLE("더블룸"),
    DIVISION("분리형"),
    MAN("남성전용"),
    UNISEX("남녀공용"),
    BOK("복층형"),
    SINGLE("싱글룸"),
    TRIPLE("트리플룸"),
    SWEET("스위트룸"),
    PENT("펜트하우스"),
    TWIN("트윈룸"),
    WOMAN("여성전용"),
    DOK("독채형"),
    DOMITORY("도미토리형"),
    ONEROOM("원룸형"),
    FAMILIY("패밀리룸"),
    ONDOL("온돌룸");


    private final String value;

    RoomTypeCategory(String value) {
        this.value = value;
    }

    public static RoomTypeCategory fromCode(String dbData) {
        return Arrays.stream(RoomTypeCategory.values())
                .filter(v -> v.getValue().equals(dbData))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("객실타입 카테고리에 %s가 존재하지 않습니다.", dbData)));
    }

    @Override
    public String getKey() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }
}
