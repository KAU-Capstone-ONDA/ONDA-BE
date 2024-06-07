package com.capstone.onda.domain.roomType.controller;


import com.capstone.onda.domain.roomType.dto.request.RoomTypeRequest;
import com.capstone.onda.domain.roomType.service.RoomTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "roomType api", description = "roomType 관련 API")
@RestController
@RequiredArgsConstructor
public class RoomTypeController {

    private final RoomTypeService roomTypeService;
    private final String AUTHORIZATION_HEADER = "Authorization";

    @PostMapping("/v1/hotel/{hotelId}/room-types/create")
    @Operation(summary = "객실타입 등록 API")
    public void postRoomType(@RequestHeader(AUTHORIZATION_HEADER) final String accessToken, @PathVariable Long hotelId, @RequestBody @Valid RoomTypeRequest request) {
        roomTypeService.post(hotelId, request);
    }


}
