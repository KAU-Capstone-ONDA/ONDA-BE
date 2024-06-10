package com.capstone.onda.domain.roomType.dto.response;

import com.capstone.onda.domain.roomType.entity.RoomType;
import com.capstone.onda.domain.roomType.enumeration.amenity.AmenityOption;
import com.capstone.onda.domain.roomType.enumeration.attraction.AttractionOption;
import com.capstone.onda.domain.roomType.enumeration.facility.FacilityOption;
import com.capstone.onda.domain.roomType.enumeration.roomType.RoomTypeCategory;
import com.capstone.onda.domain.roomType.enumeration.service.ServiceOption;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class RoomTypeListResponse {
    private Long hotelId;
    private Long roomTypeId;
    private RoomTypeCategory roomTypeName;
    private Integer totalRoom;
    private Integer people;

    private List<FacilityOption> facilityOptions = new ArrayList<>();

    private List<AttractionOption> attractionOptions = new ArrayList<>();

    private List<ServiceOption> serviceOptions = new ArrayList<>();

    private List<AmenityOption> amenityOptions = new ArrayList<>();

    public RoomTypeListResponse(RoomType roomType) {
        this.hotelId = roomType.getHotel().getId();
        this.roomTypeId = roomType.getId();
        this.roomTypeName = roomType.getRoomTypeCategory();
        this.totalRoom = roomType.getTotalRoom();
        this.people = roomType.getPeople();
    }

}
