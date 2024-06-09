package com.capstone.onda.domain.hotel.service;

import com.capstone.onda.domain.hotel.dto.request.CompetingHotelRequest;
import com.capstone.onda.domain.hotel.dto.response.HotelResponse;
import com.capstone.onda.domain.hotel.entity.Hotel;
import com.capstone.onda.domain.hotel.exception.HotelNotFound;
import com.capstone.onda.domain.hotel.repository.HotelRepository;
import com.capstone.onda.domain.hotel.util.HotelMapper;
import com.capstone.onda.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;

    public List<HotelResponse> getHotel(String hotelName) {
        if (hotelName.isBlank()) {
            throw new HotelNotFound(ErrorCode.INVALID_HOTEL_EXCEPTION);
        }

        String replacedHotel = hotelName.replace(" ", "");
        List<Hotel> hotels = hotelRepository.findAllByHotelName(replacedHotel);

        if (hotels.isEmpty()) {
            throw new HotelNotFound(ErrorCode.INVALID_HOTEL_EXCEPTION);
        }

        return hotels.stream()
            .map(HotelMapper::toHotelResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public List<HotelResponse> registerCompetingHotel(Long hotelId,
        CompetingHotelRequest competingHotelRequestDTO) {

        Hotel hotel = hotelRepository.findById(hotelId)
            .orElseThrow(() -> new HotelNotFound(ErrorCode.INVALID_HOTEL_EXCEPTION));
        Hotel competingHotel = hotelRepository.findById(
                competingHotelRequestDTO.getCompetingHotelId())
            .orElseThrow(() -> new HotelNotFound(ErrorCode.INVALID_HOTEL_EXCEPTION));

        hotel.addCompetingHotel(competingHotel);

        return hotel.getCompetingHotel().stream()
            .map(HotelMapper::toHotelResponse)
            .collect(Collectors.toList());
    }

}