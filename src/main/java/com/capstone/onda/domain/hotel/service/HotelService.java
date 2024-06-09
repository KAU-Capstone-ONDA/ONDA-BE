package com.capstone.onda.domain.hotel.service;

import com.capstone.onda.domain.hotel.dto.request.CompetingHotelRequest;
import com.capstone.onda.domain.hotel.dto.request.CompetingRoomTypeRequest;
import com.capstone.onda.domain.hotel.dto.response.HotelResponse;
import com.capstone.onda.domain.hotel.entity.Hotel;
import com.capstone.onda.domain.hotel.exception.HotelNotFound;
import com.capstone.onda.domain.hotel.repository.HotelRepository;
import com.capstone.onda.domain.hotel.util.HotelMapper;
import com.capstone.onda.domain.member.entity.Member;
import com.capstone.onda.domain.member.service.MemberService;
import com.capstone.onda.domain.roomType.dto.response.RoomTypeResponse;
import com.capstone.onda.domain.roomType.entity.RoomType;
import com.capstone.onda.domain.roomType.exception.RoomTypeNotFound;
import com.capstone.onda.domain.roomType.repository.RoomTypeRepository;
import com.capstone.onda.domain.roomType.util.RoomTypeMapper;
import com.capstone.onda.global.exception.ErrorCode;
import com.capstone.onda.global.security.dto.SecurityUser;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final RoomTypeRepository roomTypeRepository;
    private final MemberService memberService;

    public List<HotelResponse> getHotel(String hotelName) {
        if (hotelName.isBlank()) {
            throw new HotelNotFound(ErrorCode.INVALID_HOTEL_EXCEPTION);
        }

        String replacedHotel = hotelName.replace(" ", "");
        List<Hotel> hotels = hotelRepository.findAllByHotelName(replacedHotel);

        if (hotels.isEmpty()) {
            throw new HotelNotFound(ErrorCode.INVALID_HOTEL_EXCEPTION);
        }

        return hotels.stream().map(HotelMapper::toHotelResponse).collect(Collectors.toList());
    }

    @Transactional
    public List<HotelResponse> registerCompetingHotel(SecurityUser securityUser,
        CompetingHotelRequest competingHotelRequestDTO) {

        Member member = memberService.validateMember(securityUser);

        Hotel hotel = hotelRepository.findById(member.getHotel().getId())
            .orElseThrow(() -> new HotelNotFound(ErrorCode.INVALID_HOTEL_EXCEPTION));

        Hotel competingHotel = hotelRepository.findById(
                competingHotelRequestDTO.getCompetingHotelId())
            .orElseThrow(() -> new HotelNotFound(ErrorCode.INVALID_HOTEL_EXCEPTION));

        hotel.addCompetingHotel(competingHotel);

        return hotel.getCompetingHotel().stream().map(HotelMapper::toHotelResponse)
            .collect(Collectors.toList());
    }

    public List<HotelResponse> findAllCompetingHotel(SecurityUser securityUser) {

        Member member = memberService.validateMember(securityUser);

        Hotel hotel = hotelRepository.findById(member.getHotel().getId())
            .orElseThrow(() -> new HotelNotFound(ErrorCode.INVALID_HOTEL_EXCEPTION));

        return hotel.getCompetingHotel().stream().map(HotelMapper::toHotelResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public List<RoomTypeResponse> registerCompetingRoomType(SecurityUser securityUser,
        Long roomTypeId, CompetingRoomTypeRequest competingRoomTypeRequest) {

        Member member = memberService.validateMember(securityUser);

        RoomType roomType = roomTypeRepository.findById(roomTypeId)
            .orElseThrow(() -> new RoomTypeNotFound(ErrorCode.INVALID_ROOMTYPE_EXCEPTION));

        // 내 호텔에 속한 객실 타입인지 검증하는 구문
        if (!Objects.equals(member.getHotel().getId(), roomType.getHotel().getId())) {
            throw new RoomTypeNotFound(ErrorCode.NOT_EXIST_ROOMTYPE_EXCEPTION);
        }

        RoomType competingRoomType = roomTypeRepository.findById(
                competingRoomTypeRequest.getCompetingRoomTypeId())
            .orElseThrow(() -> new RoomTypeNotFound(ErrorCode.INVALID_ROOMTYPE_EXCEPTION));

        roomType.addCompetingRoomType(competingRoomType);

        return roomType.getCompetingRoomType().stream().map(
            (tmpRoomType) -> RoomTypeMapper.toRoomTypeResponse(member.getHotel().getId(),
                tmpRoomType)).collect(Collectors.toList());
    }

}