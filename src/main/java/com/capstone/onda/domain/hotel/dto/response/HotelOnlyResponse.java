package com.capstone.onda.domain.hotel.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class HotelOnlyResponse {

    private Long id;
    private String hotelName;
    private String region;
    private String city;
    private Integer star;

}
