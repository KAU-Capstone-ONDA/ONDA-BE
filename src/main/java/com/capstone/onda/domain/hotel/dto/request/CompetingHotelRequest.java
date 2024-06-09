package com.capstone.onda.domain.hotel.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompetingHotelRequest {

    @NotNull(message = "호텔Id를 입력하세요.")
    private Long competingHotelId;

}
