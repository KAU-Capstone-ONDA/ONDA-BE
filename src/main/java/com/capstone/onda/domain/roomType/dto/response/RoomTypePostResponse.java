package com.capstone.onda.domain.roomType.dto.response;

import com.capstone.onda.domain.roomType.enumeration.amenity.AmenityOption;
import com.capstone.onda.domain.roomType.enumeration.attraction.AttractionOption;
import com.capstone.onda.domain.roomType.enumeration.facility.FacilityOption;
import com.capstone.onda.domain.roomType.enumeration.roomType.RoomTypeCategory;
import com.capstone.onda.domain.roomType.enumeration.service.ServiceOption;
import java.util.List;
import lombok.Builder;

public record RoomTypePostResponse(
    Long hotelId,
    Long roomTypeId,
    RoomTypeCategory roomTypeName,
    Integer totalRoom,
    List<FacilityOption> facilityOptions,
    List<AttractionOption> attractionOptions,
    List<ServiceOption> serviceOptions,
    List<AmenityOption> amenityOptions

) {

    @Builder
    public RoomTypePostResponse(Long hotelId, Long roomTypeId, RoomTypeCategory roomTypeName,
        Integer totalRoom, List<FacilityOption> facilityOptions,
        List<AttractionOption> attractionOptions, List<ServiceOption> serviceOptions,
        List<AmenityOption> amenityOptions) {
        this.hotelId = hotelId;
        this.roomTypeId = roomTypeId;
        this.roomTypeName = roomTypeName;
        this.totalRoom = totalRoom;
        this.facilityOptions = facilityOptions;
        this.attractionOptions = attractionOptions;
        this.serviceOptions = serviceOptions;
        this.amenityOptions = amenityOptions;
    }
}
