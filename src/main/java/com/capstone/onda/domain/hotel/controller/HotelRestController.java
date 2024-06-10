package com.capstone.onda.domain.hotel.controller;

import com.capstone.onda.domain.hotel.dto.request.CompetingHotelRequest;
import com.capstone.onda.domain.hotel.dto.request.CompetingRoomTypeRequest;
import com.capstone.onda.domain.hotel.dto.response.HotelResponse;
import com.capstone.onda.domain.hotel.service.HotelService;
import com.capstone.onda.domain.roomType.dto.response.RoomTypeResponse;
import com.capstone.onda.global.common.ResponseDTO;
import com.capstone.onda.global.security.util.SecurityUtil;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class HotelRestController {

    private final HotelService hotelService;

    @GetMapping(value = "/hotel")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<HotelResponse>> getAllHotels(@RequestParam String name) {
        return ResponseDTO.res(hotelService.findAllHotel(name), "호텔 검색에 성공했습니다.");
    }

    @PostMapping("/competing-hotel")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<HotelResponse>> postCompetingHotel(
        @RequestBody @Valid CompetingHotelRequest request) {
        return ResponseDTO.res(hotelService.registerCompetingHotel(SecurityUtil.getUser(), request),
            "경쟁 호텔 등록에 성공했습니다.");
    }

    @GetMapping("/competing-hotel")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<HotelResponse>> getCompetingHotel() {
        return ResponseDTO.res(hotelService.findAllCompetingHotel(SecurityUtil.getUser()),
            "경쟁 호텔 조회에 성공했습니다.");
    }

    @GetMapping("/hotel/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<HotelResponse> getHotelInfo(@PathVariable Long hotelId) {
        return ResponseDTO.res(hotelService.findHotel(SecurityUtil.getUser(), hotelId),
            "호텔 검색에 성공했습니다.");
    }

    @PostMapping("/competing-room-type/{roomTypeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<RoomTypeResponse>> postCompetingRoomType(@PathVariable Long roomTypeId,
        @RequestBody @Valid CompetingRoomTypeRequest request) {
        return ResponseDTO.res(
            hotelService.registerCompetingRoomType(SecurityUtil.getUser(), roomTypeId, request),
            "경쟁 객실 타입 등록에 성공했습니다.");
    }

    @GetMapping("/competing-room-type/{roomTypeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<RoomTypeResponse>> getCompetingRoomType(@PathVariable Long roomTypeId) {
        return ResponseDTO.res(
            hotelService.findAllCompetingRoomType(SecurityUtil.getUser(), roomTypeId),
            "경쟁 객실 타입 조회에 성공했습니다.");
    }

}
