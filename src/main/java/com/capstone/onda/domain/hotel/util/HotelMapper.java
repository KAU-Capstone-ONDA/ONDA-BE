package com.capstone.onda.domain.hotel.util;

import com.capstone.onda.domain.hotel.dto.response.HotelResponse;
import com.capstone.onda.domain.hotel.entity.Hotel;
import com.capstone.onda.domain.member.dto.request.MemberSignUpRequest;
import com.capstone.onda.domain.roomType.util.RoomTypeMapper;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HotelMapper {

    public static Hotel toHotel(MemberSignUpRequest memberSignUpRequest) {
        return Hotel.builder()
            .hotelName(memberSignUpRequest.hotelName())
            .region(memberSignUpRequest.region())
            .city(memberSignUpRequest.city())
            .star(memberSignUpRequest.star())
            .build();
    }

    public static HotelResponse toHotelResponse(Hotel hotel) {
        return HotelResponse.builder()
            .id(hotel.getId())
            .hotelName(hotel.getHotelName())
            .region(hotel.getRegion())
            .city(hotel.getCity())
            .star(hotel.getStar())
            .roomTypeLists(hotel.getRoomTypes().stream()
                .map((roomType) -> RoomTypeMapper.toRoomTypeResponse(hotel.getId(), roomType))
                .collect(Collectors.toList()))
            .build();
    }

}