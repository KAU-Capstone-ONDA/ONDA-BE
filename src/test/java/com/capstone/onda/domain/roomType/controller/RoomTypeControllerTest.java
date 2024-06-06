package com.capstone.onda.domain.roomType.controller;


import com.capstone.onda.domain.hotel.entity.Hotel;
import com.capstone.onda.domain.hotel.repository.HotelRepository;
import com.capstone.onda.domain.roomType.dto.request.RoomTypeEdit;
import com.capstone.onda.domain.roomType.dto.request.RoomTypeRequest;
import com.capstone.onda.domain.roomType.entity.RoomType;
import com.capstone.onda.domain.roomType.enumeration.amenity.AmenityOption;
import com.capstone.onda.domain.roomType.enumeration.attraction.AttractionOption;
import com.capstone.onda.domain.roomType.enumeration.facility.FacilityOption;
import com.capstone.onda.domain.roomType.enumeration.roomType.RoomTypeCategory;
import com.capstone.onda.domain.roomType.enumeration.service.ServiceOption;
import com.capstone.onda.domain.roomType.repository.RoomTypeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RoomTypeControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @BeforeEach
    void clean() { roomTypeRepository.deleteAll(); hotelRepository.deleteAll(); }


    @Test
    @DisplayName("객실타입 등록시 DB에 값이 저장된다.")
    void test1() throws Exception{
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

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/v1/hotel/{hotelId}/room-types/create", hotel.getId())
                .contentType(APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        assertEquals(1L, roomTypeRepository.count());

        RoomType roomType = roomTypeRepository.findAll().get(0);
        assertEquals(RoomTypeCategory.ONDOL, roomType.getRoomTypeCategory());
        assertEquals(5, roomType.getTotalRoom());

    }

    @Test
    @DisplayName("객실타입 1개 조회")
    void test2() throws Exception {
        // given
        RoomType roomType = RoomType.builder()
                .roomTypeCategory(RoomTypeCategory.ONDOL)
                .totalRoom(5)
                .build();

        roomType.addFacilityOption(FacilityOption.BANQUET_HALL);
        roomType.addFacilityOption(FacilityOption.FITNESS);
        roomType.addAttractionOption(AttractionOption.FISHING);
        roomType.addServiceOption(ServiceOption.CAMPFIRE);
        roomType.addServiceOption(ServiceOption.BAR);
        roomType.addAmenityOption(AmenityOption.AIR_CONDITIONER);
        roomType.addAmenityOption(AmenityOption.BIDET);

        roomTypeRepository.save(roomType); // // 저장하여 영속화

        // expected
        mockMvc.perform(get("/v1/room-types/{roomTypeId}", roomType.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(roomType.getId()))
                .andExpect(jsonPath("$.roomTypeName").value(roomType.getRoomTypeCategory().toString()))
                .andExpect(jsonPath("$.totalRoom").value(roomType.getTotalRoom()))
                .andExpect(jsonPath("$.facilityOptions[0]").value(roomType.getFacilityOptions().get(0).toString()))
                .andExpect(jsonPath("$.facilityOptions[1]").value(roomType.getFacilityOptions().get(1).toString()))
                .andDo(print());
    }

    @Test
    @DisplayName("객실타입 여러개 조회")
    void test3() throws Exception {
        // given
        Hotel hotel = Hotel.builder()
                .hotelName("신라호텔")
                .region("서울")
                .build();

        hotelRepository.save(hotel);

        RoomType roomType1 = RoomType.builder()
                .roomTypeCategory(RoomTypeCategory.ONDOL)
                .totalRoom(5)
                .build();

        hotel.addRoomType(roomType1);
        roomTypeRepository.save(roomType1);

        RoomType roomType2 = RoomType.builder()
                .roomTypeCategory(RoomTypeCategory.DOUBLE)
                .totalRoom(3)
                .build();

        hotel.addRoomType(roomType2);
        roomTypeRepository.save(roomType2);


        // expected
        mockMvc.perform(get("/v1/hotel/{hotelId}/room-types", hotel.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].roomTypeName").value(hotel.getRoomTypes().get(0).getRoomTypeCategory().toString()))
                .andExpect(jsonPath("$[0].totalRoom").value(hotel.getRoomTypes().get(0).getTotalRoom()))
                .andDo(print());
    }

    @Test
    @DisplayName("객실타입 수정")
    void test4() throws Exception {
        // given
        RoomType roomType = RoomType.builder()
                .roomTypeCategory(RoomTypeCategory.ONDOL)
                .totalRoom(5)
                .build();

        roomType.addFacilityOption(FacilityOption.BANQUET_HALL);
        roomType.addFacilityOption(FacilityOption.FITNESS);
        roomType.addAttractionOption(AttractionOption.FISHING);
        roomType.addServiceOption(ServiceOption.CAMPFIRE);
        roomType.addServiceOption(ServiceOption.BAR);
        roomType.addAmenityOption(AmenityOption.AIR_CONDITIONER);
        roomType.addAmenityOption(AmenityOption.BIDET);

        roomTypeRepository.save(roomType);

        RoomTypeEdit roomTypeEdit = RoomTypeEdit.builder()
                .facilityOptions(List.of(FacilityOption.FITNESS))
                .attractionOptions(List.of(AttractionOption.FISHING, AttractionOption.GOLF))
                .serviceOptions(List.of())
                .build();

        // expected
        mockMvc.perform(patch("/v1/room-types/{roomTypeId}", roomType.getId())
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(roomTypeEdit)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("객실타입 삭제")
    void test5() throws Exception {
        // given
        RoomType roomType = RoomType.builder()
                .roomTypeCategory(RoomTypeCategory.ONDOL)
                .totalRoom(5)
                .build();

        roomType.addFacilityOption(FacilityOption.BANQUET_HALL);
        roomType.addFacilityOption(FacilityOption.FITNESS);
        roomType.addAttractionOption(AttractionOption.FISHING);
        roomType.addServiceOption(ServiceOption.CAMPFIRE);
        roomType.addServiceOption(ServiceOption.BAR);
        roomType.addAmenityOption(AmenityOption.AIR_CONDITIONER);
        roomType.addAmenityOption(AmenityOption.BIDET);

        roomTypeRepository.save(roomType);

        // expected
        mockMvc.perform(post("/v1/room-types/{roomTypeId}/delete", roomType.getId())
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}