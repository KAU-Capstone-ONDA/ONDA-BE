package com.capstone.onda.domain.hotel.service;

import com.capstone.onda.domain.hotel.dto.request.CompetingHotelRequest;
import com.capstone.onda.domain.hotel.dto.response.HotelResponse;
import com.capstone.onda.domain.hotel.entity.Hotel;
import com.capstone.onda.domain.hotel.exception.HotelNotFound;
import com.capstone.onda.domain.hotel.repository.HotelRepository;
import com.capstone.onda.domain.hotel.util.HotelMapper;
import com.capstone.onda.domain.member.entity.Member;
import com.capstone.onda.domain.member.exception.InvalidMemberException;
import com.capstone.onda.domain.member.repository.MemberRepository;
import com.capstone.onda.global.exception.ErrorCode;
import com.capstone.onda.global.security.dto.SecurityUser;
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
    private final MemberRepository memberRepository;

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
    public List<HotelResponse> registerCompetingHotel(SecurityUser securityUser,
        CompetingHotelRequest competingHotelRequestDTO) {

        Member member = memberRepository.findByUserEmail(securityUser.email()).orElseThrow(
            () -> new InvalidMemberException(ErrorCode.INVALID_MEMBER_EXCEPTION));

        Hotel hotel = hotelRepository.findById(member.getHotel().getId())
            .orElseThrow(() -> new HotelNotFound(ErrorCode.INVALID_HOTEL_EXCEPTION));

        Hotel competingHotel = hotelRepository.findById(
                competingHotelRequestDTO.getCompetingHotelId())
            .orElseThrow(() -> new HotelNotFound(ErrorCode.INVALID_HOTEL_EXCEPTION));

        hotel.addCompetingHotel(competingHotel);

        return hotel.getCompetingHotel().stream()
            .map(HotelMapper::toHotelResponse)
            .collect(Collectors.toList());
    }

    public List<HotelResponse> findAllCompetingHotel(SecurityUser securityUser) {

        Member member = memberRepository.findByUserEmail(securityUser.email()).orElseThrow(
            () -> new InvalidMemberException(ErrorCode.INVALID_MEMBER_EXCEPTION));

        Hotel hotel = hotelRepository.findById(member.getHotel().getId())
            .orElseThrow(() -> new HotelNotFound(ErrorCode.INVALID_HOTEL_EXCEPTION));

        return hotel.getCompetingHotel().stream()
            .map(HotelMapper::toHotelResponse)
            .collect(Collectors.toList());
    }

}