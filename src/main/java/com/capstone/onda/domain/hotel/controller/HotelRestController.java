package com.capstone.onda.domain.hotel.controller;

import com.capstone.onda.domain.hotel.dto.request.CompetingHotelRequest;
import com.capstone.onda.domain.hotel.dto.response.HotelResponse;
import com.capstone.onda.domain.hotel.service.HotelService;
import com.capstone.onda.global.common.ResponseDTO;
import com.capstone.onda.global.security.util.SecurityUtil;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/hotel")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<HotelResponse>> getAllHotels(@RequestParam String name) {
        return ResponseDTO.res(hotelService.getHotel(name), "호텔 검색에 성공했습니다.");
    }

    @PostMapping("/competing-hotel")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDTO<List<HotelResponse>> postCompetingHotel(
        @RequestBody @Valid CompetingHotelRequest request) {
        return ResponseDTO.res(hotelService.registerCompetingHotel(SecurityUtil.getUser(), request),
            "경쟁 호텔 등록에 성공했습니다.");
    }

}
