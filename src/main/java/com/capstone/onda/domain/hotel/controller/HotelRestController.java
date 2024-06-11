package com.capstone.onda.domain.hotel.controller;

import com.capstone.onda.domain.hotel.dto.request.CompetingHotelRequest;
import com.capstone.onda.domain.hotel.dto.request.CompetingRoomTypeRequest;
import com.capstone.onda.domain.hotel.dto.response.HotelOnlyResponse;
import com.capstone.onda.domain.hotel.dto.response.HotelResponse;
import com.capstone.onda.domain.hotel.service.HotelService;
import com.capstone.onda.domain.roomType.dto.response.RoomTypeResponse;
import com.capstone.onda.global.common.ResponseDTO;
import com.capstone.onda.global.security.dto.CustomOAuth2User;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseDTO<List<HotelResponse>> getAllHotels(@RequestParam String name,
        @AuthenticationPrincipal CustomOAuth2User user) {
        return ResponseDTO.res(hotelService.findAllHotel(user.getEmail(), name), "호텔 검색에 성공했습니다.");
    }

    @PostMapping("/competing-hotel")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<HotelResponse>> postCompetingHotel(
        @AuthenticationPrincipal CustomOAuth2User user,
        @RequestBody @Valid CompetingHotelRequest request) {
        log.info(user.getEmail());
        return ResponseDTO.res(hotelService.registerCompetingHotel(user.getEmail(), request),
            "경쟁 호텔 등록에 성공했습니다.");
    }

    @GetMapping("/competing-hotel")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<HotelOnlyResponse>> getCompetingHotel(
        @AuthenticationPrincipal CustomOAuth2User user) {
        return ResponseDTO.res(hotelService.findAllCompetingHotel(user.getEmail()),
            "경쟁 호텔 조회에 성공했습니다.");
    }

    @GetMapping("/hotel/{hotelId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<HotelResponse> getHotelInfo(@AuthenticationPrincipal CustomOAuth2User user,
        @PathVariable Long hotelId) {
        return ResponseDTO.res(hotelService.findHotel(user.getEmail(), hotelId),
            "호텔 검색에 성공했습니다.");
    }

    @PostMapping("/competing-room-type")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<RoomTypeResponse>> postCompetingRoomType(
        @RequestBody @Valid CompetingRoomTypeRequest request,
        @AuthenticationPrincipal CustomOAuth2User user) {
        return ResponseDTO.res(
            hotelService.registerCompetingRoomType(user.getEmail(), request),
            "경쟁 객실 타입 등록에 성공했습니다.");
    }

    @GetMapping("/competing-room-type/{roomTypeId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<RoomTypeResponse>> getCompetingRoomType(@PathVariable Long roomTypeId,
        @AuthenticationPrincipal CustomOAuth2User user) {
        return ResponseDTO.res(
            hotelService.findAllCompetingRoomType(user.getEmail(), roomTypeId),
            "경쟁 객실 타입 조회에 성공했습니다.");
    }

}
