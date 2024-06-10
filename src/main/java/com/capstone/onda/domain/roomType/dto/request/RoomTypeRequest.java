package com.capstone.onda.domain.roomType.dto.request;


import com.capstone.onda.domain.roomType.enumeration.amenity.AmenityGroup;
import com.capstone.onda.domain.roomType.enumeration.amenity.AmenityOption;
import com.capstone.onda.domain.roomType.enumeration.attraction.AttractionOption;
import com.capstone.onda.domain.roomType.enumeration.facility.FacilityOption;
import com.capstone.onda.domain.roomType.enumeration.roomType.RoomTypeCategory;
import com.capstone.onda.domain.roomType.enumeration.service.ServiceOption;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RoomTypeRequest {


    private RoomTypeCategory roomTypeName;

    private Integer totalRoom;

    private  Integer people;

    private List<FacilityOption> facilityOptions = new ArrayList<>();

    private List<AttractionOption> attractionOptions = new ArrayList<>();

    private List<ServiceOption> serviceOptions = new ArrayList<>();

    private List<AmenityGroup> amenityGroups = new ArrayList<>();

    private List<AmenityOption> amenityOptions = new ArrayList<>();

    @Builder
    public RoomTypeRequest(RoomTypeCategory roomTypeName, Integer totalRoom, Integer people, List<FacilityOption> facilityOptions, List<AttractionOption> attractionOptions, List<ServiceOption> serviceOptions, List<AmenityGroup> amenityGroups, List<AmenityOption> amenityOptions) {
        this.roomTypeName = roomTypeName;
        this.totalRoom = totalRoom;
        this.people = people;
        this.facilityOptions = facilityOptions;
        this.attractionOptions = attractionOptions;
        this.serviceOptions = serviceOptions;
        this.amenityGroups = amenityGroups;
        this.amenityOptions = amenityOptions;
    }
}


