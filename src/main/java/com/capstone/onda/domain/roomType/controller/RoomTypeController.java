package com.capstone.onda.domain.roomType.controller;


import com.capstone.onda.domain.roomType.EnumDTO;
import com.capstone.onda.domain.roomType.EnumManager;
import com.capstone.onda.domain.roomType.dto.request.RoomTypeEdit;
import com.capstone.onda.domain.roomType.dto.request.RoomTypeRequest;
import com.capstone.onda.domain.roomType.dto.response.RoomTypeListResponse;
import com.capstone.onda.domain.roomType.dto.response.RoomTypeResponse;
import com.capstone.onda.domain.roomType.enumeration.roomType.RoomTypeCategory;
import com.capstone.onda.domain.roomType.service.RoomTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RoomTypeController {

    private final RoomTypeService roomTypeService;
    private final String AUTHORIZATION_HEADER = "Authorization";

    @PostMapping("/v1/hotel/{hotelId}/room-types/create")
    public void postRoomType(@RequestHeader(AUTHORIZATION_HEADER) final String accessToken, @PathVariable Long hotelId, @RequestBody @Valid RoomTypeRequest request) {
        roomTypeService.post(hotelId, request);
    }

    @GetMapping("/v1/room-types/{roomTypeId}")
    public RoomTypeResponse get(@RequestHeader(AUTHORIZATION_HEADER) final String accessToken, @PathVariable Long roomTypeId) {
        return roomTypeService.get(roomTypeId);
    }

    @GetMapping("/v1/hotel/{hotelId}/room-types")
    public List<RoomTypeListResponse> getList(@RequestHeader(AUTHORIZATION_HEADER) final String accessToken, @PathVariable Long hotelId) {
        return roomTypeService.getList(hotelId);
    }

    @PatchMapping("/v1/room-types/{roomTypeId}")
    public void edit(@RequestHeader(AUTHORIZATION_HEADER) final String accessToken, @PathVariable Long roomTypeId, @RequestBody @Valid RoomTypeEdit request) {
        roomTypeService.edit(roomTypeId, request);
    }

    @PostMapping("/v1/room-types/{roomTypeId}/delete")
    public void delete(@RequestHeader(AUTHORIZATION_HEADER) final String accessToken, @PathVariable Long roomTypeId) {
        roomTypeService.delete(roomTypeId);
    }


}
