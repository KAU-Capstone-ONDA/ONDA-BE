package com.capstone.onda.domain.member.dto.response;

import lombok.Builder;

public record MemberResponse(
    Long id,
    String email,
    String name,
    String hotelName,
    String region,
    String city
) {

    @Builder
    public MemberResponse(Long id, String email, String name, String hotelName, String region,
        String city) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.hotelName = hotelName;
        this.region = region;
        this.city = city;
    }
}
