package com.capstone.onda.domain.roomType.service;


import com.capstone.onda.domain.hotel.entity.Hotel;
import com.capstone.onda.domain.hotel.exception.HotelNotFound;
import com.capstone.onda.domain.hotel.repository.HotelRepository;
import com.capstone.onda.domain.member.entity.Member;
import com.capstone.onda.domain.member.exception.InvalidMemberException;
import com.capstone.onda.domain.member.repository.MemberRepository;
import com.capstone.onda.domain.roomType.dto.request.RoomTypeEdit;
import com.capstone.onda.domain.roomType.dto.request.RoomTypeRequest;
import com.capstone.onda.domain.roomType.dto.response.RoomTypeListResponse;
import com.capstone.onda.domain.roomType.dto.response.RoomTypePostResponse;
import com.capstone.onda.domain.roomType.dto.response.RoomTypeResponse;
import com.capstone.onda.domain.roomType.entity.RoomType;
import com.capstone.onda.domain.roomType.enumeration.amenity.AmenityOption;
import com.capstone.onda.domain.roomType.enumeration.attraction.AttractionOption;
import com.capstone.onda.domain.roomType.enumeration.facility.FacilityOption;
import com.capstone.onda.domain.roomType.enumeration.roomType.RoomTypeCategory;
import com.capstone.onda.domain.roomType.enumeration.service.ServiceOption;
import com.capstone.onda.domain.roomType.exception.RoomTypeNotFound;
import com.capstone.onda.domain.roomType.repository.RoomTypeRepository;
import com.capstone.onda.domain.roomType.util.RoomTypeMapper;
import com.capstone.onda.global.exception.ErrorCode;
import com.capstone.onda.global.security.dto.SecurityUser;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;
    private final HotelRepository hotelRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public RoomTypePostResponse postRoomType(SecurityUser securityUser, RoomTypeRequest request) {
        Member member = memberRepository.findByUserEmail(securityUser.email()).orElseThrow(
            () -> new InvalidMemberException(ErrorCode.INVALID_MEMBER_EXCEPTION));
        Hotel hotel = hotelRepository.findById(member.getHotel().getId())
            .orElseThrow(() -> new HotelNotFound(ErrorCode.INVALID_HOTEL_EXCEPTION));

        RoomTypeCategory roomTypeName = request.getRoomTypeName();
        Integer totalRoom = request.getTotalRoom();

        RoomType roomType = RoomType.builder()
            .roomTypeCategory(roomTypeName)
            .totalRoom(totalRoom)
            .build();

        List<FacilityOption> facilityOptions = request.getFacilityOptions();
        List<AttractionOption> attractionOptions = request.getAttractionOptions();
        List<ServiceOption> serviceOptions = request.getServiceOptions();
        List<AmenityOption> amenityOptions = request.getAmenityOptions();

        // Null 체크 후 빈 리스트로 초기화
        if (facilityOptions == null) {
            facilityOptions = new ArrayList<>();
        }
        if (attractionOptions == null) {
            attractionOptions = new ArrayList<>();
        }
        if (serviceOptions == null) {
            serviceOptions = new ArrayList<>();
        }
        if (amenityOptions == null) {
            amenityOptions = new ArrayList<>();
        }

        for (FacilityOption facilityOption : facilityOptions) {
            roomType.addFacilityOption(facilityOption);
        }

        for (AttractionOption attractionOption : attractionOptions) {
            roomType.addAttractionOption(attractionOption);
        }

        for (ServiceOption serviceOption : serviceOptions) {
            roomType.addServiceOption(serviceOption);
        }

        for (AmenityOption amenityOption : amenityOptions) {
            roomType.addAmenityOption(amenityOption);
        }

        hotel.addRoomType(roomType);

        RoomType savedRoomType = roomTypeRepository.save(roomType);
        return RoomTypeMapper.toRoomTypePostResponse(hotel.getId(), savedRoomType);
    }

    public RoomTypeResponse getOneRoomType(Long roomTypeId) {
        RoomType roomType = roomTypeRepository.findById(roomTypeId)
            .orElseThrow(() -> new RoomTypeNotFound(ErrorCode.INVALID_ROOMTYPE_EXCEPTION));

        return RoomTypeMapper.toRoomTypeResponse(roomType);
    }

    public List<RoomTypeListResponse> getListRoomType(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
            .orElseThrow(() -> new HotelNotFound(ErrorCode.INVALID_HOTEL_EXCEPTION));

        return roomTypeRepository.findByHotelId(hotel.getId()).stream()
            .map(RoomTypeListResponse::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public void editRoomType(Long id, RoomTypeEdit roomTypeEdit) {
        RoomType roomType = roomTypeRepository.findById(id)
            .orElseThrow(() -> new RoomTypeNotFound(ErrorCode.INVALID_ROOMTYPE_EXCEPTION));

        roomType.edit(roomTypeEdit.getFacilityOptions(), roomTypeEdit.getAttractionOptions(),
            roomTypeEdit.getServiceOptions(), roomTypeEdit.getAmenityOptions());
    }

    @Transactional
    public void deleteRoomType(Long id) {
        RoomType roomType = roomTypeRepository.findById(id)
                .orElseThrow(() -> new RoomTypeNotFound(ErrorCode.INVALID_ROOMTYPE_EXCEPTION));

        roomTypeRepository.delete(roomType);
    }

}



