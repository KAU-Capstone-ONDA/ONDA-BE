package com.capstone.onda.domain.roomType.entity;



import com.capstone.onda.domain.hotel.entity.Hotel;
import com.capstone.onda.domain.roomType.enumeration.amenity.AmenityConverter;
import com.capstone.onda.domain.roomType.enumeration.amenity.AmenityOption;
import com.capstone.onda.domain.roomType.enumeration.attraction.AttractionConverter;
import com.capstone.onda.domain.roomType.enumeration.attraction.AttractionOption;
import com.capstone.onda.domain.roomType.enumeration.facility.FacilityConverter;
import com.capstone.onda.domain.roomType.enumeration.facility.FacilityOption;
import com.capstone.onda.domain.roomType.enumeration.roomType.RoomTypeCategory;
import com.capstone.onda.domain.roomType.enumeration.roomType.RoomTypeCategoryConverter;
import com.capstone.onda.domain.roomType.enumeration.service.ServiceConverter;
import com.capstone.onda.domain.roomType.enumeration.service.ServiceOption;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(
        indexes = {
                @Index(name = "IDX_ROOMTYPE_HOTEL_ID", columnList = "hotel_id")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("객실타입 식별자")
    private Long Id;

    @Comment("전체 객실 수")
    private Integer totalRoom;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @Convert(converter = RoomTypeCategoryConverter.class)
    private RoomTypeCategory roomTypeCategory;

    @Convert(converter = FacilityConverter.class)
    private List<FacilityOption> facilityOptions = new ArrayList<>();

    @Convert(converter = AttractionConverter.class)
    private List<AttractionOption> attractionOptions = new ArrayList<>();

    @Convert(converter = ServiceConverter.class)
    private List<ServiceOption> serviceOptions = new ArrayList<>();

    @Convert(converter = AmenityConverter.class)
    private List<AmenityOption> amenityOptions = new ArrayList<>();




    @Builder
    public RoomType(Integer totalRoom, RoomTypeCategory roomTypeCategory) {
        this.totalRoom = totalRoom;
        this.roomTypeCategory = roomTypeCategory;
        this.facilityOptions = new ArrayList<>();
        this.attractionOptions = new ArrayList<>();
        this.serviceOptions = new ArrayList<>();
        this.amenityOptions = new ArrayList<>();
    }


    public void edit(List<FacilityOption> facilityOptions, List<AttractionOption> attractionOptions, List<ServiceOption> serviceOptions, List<AmenityOption> amenityOptions) {
        this.facilityOptions = facilityOptions != null ? facilityOptions : new ArrayList<>();
        this.attractionOptions = attractionOptions != null ? attractionOptions : new ArrayList<>();
        this.serviceOptions = serviceOptions != null ? serviceOptions : new ArrayList<>();
        this.amenityOptions = amenityOptions != null ? amenityOptions : new ArrayList<>();
    }

    public void setHotel(Hotel hotel) { this.hotel = hotel; }

    public void addFacilityOption(FacilityOption facilityOption) {
        facilityOptions.add(facilityOption);
    }

    public void addAttractionOption(AttractionOption attractionOption) {
        attractionOptions.add(attractionOption);
    }

    public void addServiceOption(ServiceOption serviceOption) {
        serviceOptions.add(serviceOption);
    }

    public void addAmenityOption(AmenityOption amenityOption) {
        amenityOptions.add(amenityOption);
    }


}
