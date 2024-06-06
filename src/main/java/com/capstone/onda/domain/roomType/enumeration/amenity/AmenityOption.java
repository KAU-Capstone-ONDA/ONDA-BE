package com.capstone.onda.domain.roomType.enumeration.amenity;




import com.capstone.onda.domain.roomType.EnumManager;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AmenityOption implements EnumManager {
    SOFA("쇼파"),
    GAS_STOVE("가스레인지/인덕션"),
    RICE_COOKER("전기밥솥"),
    AIR_CONDITIONER("에어컨"),
    TV("TV"),
    TABLE("식탁"),
    VOD("VOD"),
    PARTY_ROOM("파티룸"),
    FIREPLACE("벽난로"),

    REFRIGERATOR("냉장고"),
    COOKING_TOOL("취사도구"),
    COFFEE_POT("커피포트"),
    SEASONING("기본양념"),
    MICROWAVE("전자레인지"),
    MINIBAR("미니바"),

    SPA("스파/월풀"),
    TOILET("화장실"),
    BIDET("비데"),
    DRIER("드라이기"),
    TOWEL("타월"),
    BATH_GOODS("욕실용품"),
    BATHTUB("욕조"),

    SMOKING("흡연가능"),
    IRON("다리미"),
    WIFI("Wi-Fi"),
    BOTTLED_WATER("무료 생수"),
    BARBECUE("개별/테라스 바베큐"),
    POOL("개별 수영장");



    private final String viewName;

    AmenityOption(String viewName) {
        this.viewName = viewName;
    }

    public static AmenityOption fromCode(String dbData) {
        return Arrays.stream(AmenityOption.values())
                .filter(v -> v.getViewName().equals(dbData))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("어메니티 옵션에 %s가 존재하지 않습니다.", dbData)));
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
