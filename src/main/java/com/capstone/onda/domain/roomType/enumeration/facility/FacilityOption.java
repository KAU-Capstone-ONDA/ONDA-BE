package com.capstone.onda.domain.roomType.enumeration.facility;


import com.capstone.onda.domain.roomType.EnumManager;
import lombok.Getter;



@Getter
public enum FacilityOption implements EnumManager {
    CAFETERIA("매점/편의점"),
    SWIMMING_POOL("수영장"),
    NORAEBANG("노래방"),
    BARBECUE_PATIO("바베큐장"),
    BASKETBALL_COURT("농구장"),
    SEMINAR_ROOM("세미나실"),
    CAFE("카페"),
    FITNESS("피트니스"),
    PUBLIC_SPA("공용스파"),
    PUBLIC_TOILET("공용화장실"),
    RESTAURANT("레스토랑"),
    BUSINESS_CENTER("비즈니스 센터"),
    BANQUET_HALL("연회장"),
    ROOFTOP("루프탑"),
    PUBLIC_KITCHEN("공용주방"),
    SAUNA("사우나"),
    CHILDCARE("유아시설"),
    HOT_SPRING("온천"),
    KIDS_PLAY("키즈플레이룸"),
    TABLE_TENNIS("탁구장"),
    WATERPARK("워터파크"),
    GOLF("골프장"),
    SPA_WHIRLPOOL("스파/월풀"),
    FOOT_VOLLEYBALL("족구장"),
    FUTSAL("축구장/풋살장"),
    JJIMJILBANG("찜질방"),
    WATER_SLIDE("워터슬라이드"),
    PUBLIC_SHOWER("공용샤워실");




    private final String viewName;

    FacilityOption(String viewName) {
        this.viewName = viewName;
    }

    public static FacilityOption fromCode(String dbData) {
        for (FacilityOption option : FacilityOption.values()) {
            if (option.viewName.equals(dbData)) {
                return option;
            }
        }
        throw new IllegalArgumentException(String.format("시설 옵션에 %s가 존재하지 않습니다.", dbData));
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

