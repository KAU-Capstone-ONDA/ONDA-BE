package com.capstone.onda.domain.roomType.entity;


import static jakarta.persistence.FetchType.LAZY;

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
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

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

    @Comment("투숙 인원수")
    private Integer people;

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

    @ManyToMany
    @JoinTable(
        name = "competing_room_type",
        joinColumns = @JoinColumn(name = "room_type_id"),
        inverseJoinColumns = @JoinColumn(name = "competing_room_type_id")
    )
    private Set<RoomType> competingRoomType = new HashSet<>();

    @Builder
    public RoomType(Integer totalRoom, RoomTypeCategory roomTypeCategory, Integer people) {
        this.totalRoom = totalRoom;
        this.roomTypeCategory = roomTypeCategory;
        this.people = people;
        this.facilityOptions = new ArrayList<>();
        this.attractionOptions = new ArrayList<>();
        this.serviceOptions = new ArrayList<>();
        this.amenityOptions = new ArrayList<>();
    }


    public void edit(List<FacilityOption> facilityOptions, List<AttractionOption> attractionOptions,
        List<ServiceOption> serviceOptions, List<AmenityOption> amenityOptions) {
        this.facilityOptions = facilityOptions != null ? facilityOptions : new ArrayList<>();
        this.attractionOptions = attractionOptions != null ? attractionOptions : new ArrayList<>();
        this.serviceOptions = serviceOptions != null ? serviceOptions : new ArrayList<>();
        this.amenityOptions = amenityOptions != null ? amenityOptions : new ArrayList<>();
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

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

    public void addCompetingRoomType(RoomType competingRoomType) {
        this.competingRoomType.add(competingRoomType);
    }


}
