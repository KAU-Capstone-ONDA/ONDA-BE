package com.capstone.onda.domain.roomType.service;

import com.capstone.onda.domain.hotel.entity.Hotel;
import com.capstone.onda.domain.hotel.repository.HotelRepository;
import com.capstone.onda.domain.roomType.dto.request.RoomTypeRequest;
import com.capstone.onda.domain.roomType.entity.RoomType;
import com.capstone.onda.domain.roomType.enumeration.roomType.RoomTypeCategory;
import com.capstone.onda.domain.roomType.repository.RoomTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RoomTypeServiceTest {

    @Autowired
    private RoomTypeService roomTypeService;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @BeforeEach
    void clean() {
        roomTypeRepository.deleteAll();
        hotelRepository.deleteAll();
    }

    @Test
    @DisplayName("객실타입 등록")
    void test1() {
        // given
        Hotel hotel = Hotel.builder()
                .hotelName("신라호텔")
                .region("서울")
                .build();

        hotelRepository.save(hotel);

        RoomTypeRequest request = RoomTypeRequest.builder()
                .roomTypeName(RoomTypeCategory.ONDOL)
                .totalRoom(5)
                .build();

        // when
        roomTypeService.post(hotel.getId(), request);

        // then
        assertEquals(1L, roomTypeRepository.count());
        RoomType roomType = roomTypeRepository.findAll().get(0);
        assertEquals(RoomTypeCategory.ONDOL, roomType.getRoomTypeCategory());
        assertEquals(5, roomType.getTotalRoom());
    }

}