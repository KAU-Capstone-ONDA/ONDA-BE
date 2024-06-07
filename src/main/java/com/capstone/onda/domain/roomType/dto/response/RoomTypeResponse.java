package com.capstone.onda.domain.roomType.dto.response;


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
public class RoomTypeResponse {

    private Long id;
    private RoomTypeCategory roomTypeName;
    private Integer totalRoom;

    private List<FacilityOption> facilityOptions = new ArrayList<>();

    private List<AttractionOption> attractionOptions = new ArrayList<>();

    private List<ServiceOption> serviceOptions = new ArrayList<>();

    private List<AmenityOption> amenityOptions = new ArrayList<>();


    @Builder
    public RoomTypeResponse(Long id, RoomTypeCategory roomTypeName, Integer totalRoom, List<FacilityOption> facilityOptions, List<AttractionOption> attractionOptions, List<ServiceOption> serviceOptions, List<AmenityOption> amenityOptions) {
        this.id = id;
        this.roomTypeName = roomTypeName;
        this.totalRoom = totalRoom;
        this.facilityOptions = facilityOptions;
        this.attractionOptions = attractionOptions;
        this.serviceOptions = serviceOptions;
        this.amenityOptions = amenityOptions;
    }
}
