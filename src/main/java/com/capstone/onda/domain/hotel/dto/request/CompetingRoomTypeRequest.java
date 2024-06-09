package com.capstone.onda.domain.hotel.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompetingRoomTypeRequest {

    @NotNull(message = "객실타입 Id를 입력하세요.")
    private Long competingRoomTypeId;

}
