package com.capstone.onda.domain.roomType.controller;


import com.capstone.onda.domain.roomType.dto.request.RoomTypeRequest;
import com.capstone.onda.domain.roomType.service.RoomTypeService;
import com.capstone.onda.global.common.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "roomType api", description = "roomType 관련 API")
@RestController
@RequiredArgsConstructor
public class RoomTypeController {

    private final RoomTypeService roomTypeService;
    private final String AUTHORIZATION_HEADER = "Authorization";

    @PostMapping("/v1/hotel/{hotelId}/room-types/create")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "객실타입 등록 API")
    public ResponseDTO<String> postRoomType(@RequestHeader(AUTHORIZATION_HEADER) final String accessToken, @PathVariable Long hotelId, @RequestBody @Valid RoomTypeRequest request) {
        roomTypeService.postRoomType(hotelId, request);
        return ResponseDTO.res("객실타입 등록에 성공했습니다.");
    }


}
