package com.capstone.onda.domain.roomType.service;


import com.capstone.onda.domain.hotel.entity.Hotel;
import com.capstone.onda.domain.hotel.exception.HotelNotFound;
import com.capstone.onda.domain.hotel.repository.HotelRepository;
import com.capstone.onda.domain.roomType.dto.request.RoomTypeRequest;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;
    private final HotelRepository hotelRepository;


    @Transactional
    public void postRoomType(Long hotelId, RoomTypeRequest request) {
        Hotel hotel = hotelRepository.findById(hotelId)
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

        roomTypeRepository.save(roomType);
    }

    public RoomTypeResponse getOneRoomType(Long roomTypeId) {
        RoomType roomType = roomTypeRepository.findById(roomTypeId)
                .orElseThrow(() -> new RoomTypeNotFound(ErrorCode.INVALID_ROOMTYPE_EXCEPTION));

        return RoomTypeMapper.toRoomTypeResponse(roomType);
    }

}



