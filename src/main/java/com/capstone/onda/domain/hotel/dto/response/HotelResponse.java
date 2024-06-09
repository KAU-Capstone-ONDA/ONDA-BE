package com.capstone.onda.domain.hotel.dto.response;

import com.capstone.onda.domain.roomType.dto.response.RoomTypeResponse;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class HotelResponse {

    private Long id;
    private String hotelName;
    private String region;
    private String city;
    private Integer star;
    private List<RoomTypeResponse> roomTypeLists;

}
