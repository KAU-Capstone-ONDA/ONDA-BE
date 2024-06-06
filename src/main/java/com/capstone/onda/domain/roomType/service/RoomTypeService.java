package com.capstone.onda.domain.roomType.service;


import com.capstone.onda.domain.hotel.entity.Hotel;
import com.capstone.onda.domain.hotel.exception.HotelNotFound;
import com.capstone.onda.domain.hotel.repository.HotelRepository;
import com.capstone.onda.domain.roomType.dto.request.RoomTypeEdit;
import com.capstone.onda.domain.roomType.dto.request.RoomTypeRequest;
import com.capstone.onda.domain.roomType.dto.response.RoomTypeListResponse;
import com.capstone.onda.domain.roomType.dto.response.RoomTypeResponse;
import com.capstone.onda.domain.roomType.entity.RoomType;
import com.capstone.onda.domain.roomType.enumeration.amenity.AmenityOption;
import com.capstone.onda.domain.roomType.enumeration.attraction.AttractionOption;
import com.capstone.onda.domain.roomType.enumeration.facility.FacilityOption;
import com.capstone.onda.domain.roomType.enumeration.roomType.RoomTypeCategory;
import com.capstone.onda.domain.roomType.enumeration.service.ServiceOption;
import com.capstone.onda.domain.roomType.exception.RoomTypeNotFound;
import com.capstone.onda.domain.roomType.repository.RoomTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomTypeService {

    private final RoomTypeRepository roomTypeRepository;
    private final HotelRepository hotelRepository;


    @Transactional
    public void post(Long hotelId, RoomTypeRequest request) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(HotelNotFound::new);

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

    public RoomTypeResponse get(Long roomTypeId) {
        RoomType roomType = roomTypeRepository.findById(roomTypeId)
                .orElseThrow(RoomTypeNotFound::new);

        return RoomTypeResponse.builder()
                .id(roomType.getId())
                .roomTypeName(roomType.getRoomTypeCategory())
                .totalRoom(roomType.getTotalRoom())
                .facilityOptions(roomType.getFacilityOptions())
                .attractionOptions(roomType.getAttractionOptions())
                .serviceOptions(roomType.getServiceOptions())
                .amenityOptions(roomType.getAmenityOptions())
                .build();
    }

    public List<RoomTypeListResponse> getList(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
        .orElseThrow(HotelNotFound::new);

        return roomTypeRepository.findByHotelId(hotel.getId()).stream()
                .map(RoomTypeListResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long id, RoomTypeEdit roomTypeEdit) {
        RoomType roomType = roomTypeRepository.findById(id)
                .orElseThrow(RoomTypeNotFound::new);

        roomType.edit(roomTypeEdit.getFacilityOptions(), roomTypeEdit.getAttractionOptions(), roomTypeEdit.getServiceOptions(), roomTypeEdit.getAmenityOptions());
    }

    @Transactional
    public void delete(Long id) {
        RoomType roomType = roomTypeRepository.findById(id)
                .orElseThrow(RoomTypeNotFound::new);

        roomTypeRepository.delete(roomType);
    }

}



