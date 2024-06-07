package com.capstone.onda.domain.hotel.util;

import com.capstone.onda.domain.hotel.entity.Hotel;
import com.capstone.onda.domain.member.dto.request.MemberSignUpRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HotelMapper {

    public static Hotel toHotel(MemberSignUpRequest memberSignUpRequest) {
        return Hotel.builder()
            .hotelName(memberSignUpRequest.hotelName())
            .region(memberSignUpRequest.region())
            .city(memberSignUpRequest.city())
            .build();
    }

}