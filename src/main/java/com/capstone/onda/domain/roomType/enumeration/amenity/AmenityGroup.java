package com.capstone.onda.domain.roomType.enumeration.amenity;


import java.util.Arrays;

public enum AmenityGroup {
    FURNITURE("가구", new AmenityOption[] {
            AmenityOption.SOFA, AmenityOption.GAS_STOVE, AmenityOption.RICE_COOKER, AmenityOption.AIR_CONDITIONER, AmenityOption.TV,
            AmenityOption.TABLE, AmenityOption.VOD, AmenityOption.PARTY_ROOM, AmenityOption.FIREPLACE
    }),
    COOKING("요리", new AmenityOption[] {
            AmenityOption.REFRIGERATOR, AmenityOption.COOKING_TOOL, AmenityOption.COFFEE_POT, AmenityOption.SEASONING, AmenityOption.MICROWAVE, AmenityOption.MINIBAR
    }),
    BATHROOM("욕실", new AmenityOption[] {
            AmenityOption.SPA, AmenityOption.TOILET, AmenityOption.BIDET, AmenityOption.DRIER, AmenityOption.TOWEL, AmenityOption.BATH_GOODS, AmenityOption.BATHTUB
    }),
    ETC("기타", new AmenityOption[] {
            AmenityOption.SMOKING, AmenityOption.IRON, AmenityOption.WIFI, AmenityOption.BOTTLED_WATER, AmenityOption.BARBECUE, AmenityOption.POOL
    }),
    EMPTY("없음", new AmenityOption[] {});

    private final String viewName;
    private final AmenityOption[] containAmenity;

    AmenityGroup(String viewName, AmenityOption[] containAmenity) {
        this.viewName = viewName;
        this.containAmenity = containAmenity;
    }

    public static AmenityGroup findGroup(AmenityOption searchTarget) {
        return Arrays.stream(AmenityGroup.values())
                .filter(group -> hasAmenityOption(group, searchTarget))
                .findAny()
                .orElse(AmenityGroup.EMPTY);
    }

    private static boolean hasAmenityOption(AmenityGroup from, AmenityOption searchTarget) {
        return Arrays.stream(from.containAmenity)
                .anyMatch(containAmenity -> containAmenity == searchTarget);
    }

    public String getViewName() {
        return viewName;
    }

    public AmenityOption[] getContainAmenity() {
        return containAmenity;
    }
}

