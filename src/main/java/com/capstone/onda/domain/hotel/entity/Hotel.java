package com.capstone.onda.domain.hotel.entity;


import com.capstone.onda.domain.roomType.entity.RoomType;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("호텔 식별자")
    private Long id;

    @Column(nullable = false)
    @Comment("호텔 이름")
    private String hotelName;

    @Comment("지역")
    private String region;

    @Comment("도시")
    private String city;

    @Comment("등급")
    private Integer star;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<RoomType> roomTypes = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "competing_hotel",
        joinColumns = @JoinColumn(name = "hotel_id"),
        inverseJoinColumns = @JoinColumn(name = "competing_hotel_id")
    )
    private Set<Hotel> competingHotel = new HashSet<>();

    @Builder
    public Hotel(Long id, String hotelName, String region, String city, Integer star) {
        this.id = id;
        this.hotelName = hotelName;
        this.region = region;
        this.city = city;
        this.star = star;
    }

    public void addRoomType(RoomType roomType) {
        roomType.setHotel(this);
        this.roomTypes.add(roomType);
    }

    public void addCompetingHotel(Hotel competingHotel) {
        this.competingHotel.add(competingHotel);
    }

}