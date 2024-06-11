package com.capstone.onda.domain.roomType.service;


import com.capstone.onda.domain.hotel.entity.Hotel;
import com.capstone.onda.domain.hotel.exception.HotelNotFound;
import com.capstone.onda.domain.hotel.repository.HotelRepository;
import com.capstone.onda.domain.member.entity.Member;
import com.capstone.onda.domain.member.repository.MemberRepository;
import com.capstone.onda.domain.member.service.MemberService;
import com.capstone.onda.domain.roomType.dto.request.RoomTypeEdit;
import com.capstone.onda.domain.roomType.dto.request.RoomTypeRequest;
import com.capstone.onda.domain.roomType.dto.response.RoomTypeEditResponse;
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
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Transactional
    public RoomTypePostResponse postRoomType(String userEmail, RoomTypeRequest request) {
        Member member = memberService.validateMember(userEmail);
        Hotel hotel = hotelRepository.findById(member.getHotel().getId())
            .orElseThrow(() -> new HotelNotFound(ErrorCode.INVALID_HOTEL_EXCEPTION));

        RoomTypeCategory roomTypeName = request.getRoomTypeName();
        Integer totalRoom = request.getTotalRoom();
        Integer people = request.getPeople();

        RoomType roomType = RoomType.builder()
            .roomTypeCategory(roomTypeName)
            .totalRoom(totalRoom)
            .people(people)
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

    public RoomTypeResponse getOneRoomType(String userEmail, Long roomTypeId) {
        Member member = memberService.validateMember(userEmail);
        Hotel hotel = hotelRepository.findById(member.getHotel().getId())
            .orElseThrow(() -> new HotelNotFound(ErrorCode.INVALID_HOTEL_EXCEPTION));
        RoomType roomType = roomTypeRepository.findById(roomTypeId)
            .orElseThrow(() -> new RoomTypeNotFound(ErrorCode.INVALID_ROOMTYPE_EXCEPTION));

        return RoomTypeMapper.toRoomTypeResponse(hotel.getId(), roomType);
    }

    public List<RoomTypeListResponse> getListRoomType(String userEmail) {
        Member member = memberService.validateMember(userEmail);
        Hotel hotel = hotelRepository.findById(member.getHotel().getId())
            .orElseThrow(() -> new HotelNotFound(ErrorCode.INVALID_HOTEL_EXCEPTION));

        return roomTypeRepository.findByHotelId(hotel.getId()).stream()
            .map(RoomTypeMapper::toRoomTypeListResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public RoomTypeEditResponse editRoomType(String userEmail, Long roomTypeId,
        RoomTypeEdit roomTypeEdit) {
        Member member = memberService.validateMember(userEmail);
        Hotel hotel = hotelRepository.findById(member.getHotel().getId())
            .orElseThrow(() -> new HotelNotFound(ErrorCode.INVALID_HOTEL_EXCEPTION));
        RoomType roomType = roomTypeRepository.findById(roomTypeId)
            .orElseThrow(() -> new RoomTypeNotFound(ErrorCode.INVALID_ROOMTYPE_EXCEPTION));

        List<FacilityOption> facilityOptions = roomTypeEdit.getFacilityOptions();
        List<AttractionOption> attractionOptions = roomTypeEdit.getAttractionOptions();
        List<ServiceOption> serviceOptions = roomTypeEdit.getServiceOptions();
        List<AmenityOption> amenityOptions = roomTypeEdit.getAmenityOptions();

        roomType.edit(facilityOptions, attractionOptions, serviceOptions, amenityOptions);

        RoomType savedRoomType = roomTypeRepository.save(roomType);

        return RoomTypeMapper.toRoomTypeEditResponse(hotel.getId(), savedRoomType);
    }

    @Transactional
    public void deleteRoomType(String userEmail, Long id) {
        Member member = memberService.validateMember(userEmail);
        Hotel hotel = hotelRepository.findById(member.getHotel().getId())
            .orElseThrow(() -> new HotelNotFound(ErrorCode.INVALID_HOTEL_EXCEPTION));
        RoomType roomType = roomTypeRepository.findById(id)
            .orElseThrow(() -> new RoomTypeNotFound(ErrorCode.INVALID_ROOMTYPE_EXCEPTION));

        roomTypeRepository.delete(roomType);
    }

}



