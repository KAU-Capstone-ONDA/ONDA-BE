package com.capstone.onda.domain.roomType.service;

import com.capstone.onda.domain.hotel.entity.Hotel;
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
import com.capstone.onda.domain.roomType.repository.RoomTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

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

    @Test
    @DisplayName("객실타입 1개 조회")
    void test2() {
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

        roomType = roomTypeRepository.save(roomType); // // 저장하여 영속화

        // when
        RoomTypeResponse response = roomTypeService.get(roomType.getId());

        // then
        assertNotNull(response);
        assertEquals(1L, roomTypeRepository.count());
        assertTrue(response.getFacilityOptions().containsAll(Arrays.asList(FacilityOption.BANQUET_HALL, FacilityOption.FITNESS)));
        assertTrue(response.getAttractionOptions().contains(AttractionOption.FISHING));

    }

    @Test
    @DisplayName("객실타입 여러개 조회")
    void test3() {
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

        // when
        List<RoomTypeListResponse> roomTypes = roomTypeService.getList(hotel.getId());

        // then
        assertEquals(2L, roomTypes.size());
        assertEquals(RoomTypeCategory.ONDOL, roomTypes.get(0).getRoomTypeName());
        assertEquals(3, roomTypes.get(1).getTotalRoom());
    }

    @Test
    @DisplayName("객실타입 수정")
    void test4() {
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

        RoomTypeEdit roomTypeEdit = RoomTypeEdit.builder()
                .facilityOptions(List.of(FacilityOption.FITNESS))
                .attractionOptions(List.of(AttractionOption.FISHING, AttractionOption.GOLF))
                .serviceOptions(List.of())
                .build();

        // when
        roomTypeService.edit(roomType.getId(), roomTypeEdit);

        // then
        RoomType changedRoomType = roomTypeRepository.findById(roomType.getId())
                .orElseThrow(() -> new RuntimeException("객실타입이 존재하지 않습니다. id=" + roomType.getId()));

        assertEquals(0L, changedRoomType.getServiceOptions().size());
        assertEquals(1L, changedRoomType.getFacilityOptions().size());
        assertEquals("FISHING", changedRoomType.getAttractionOptions().get(0).toString());
        assertEquals(AttractionOption.GOLF, changedRoomType.getAttractionOptions().get(1));

    }

    @Test
    @DisplayName("객실타입 삭제")
    void test5() {
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


        // when
        roomTypeService.delete(roomType.getId());

        // then
        assertEquals(0, roomTypeRepository.count());
    }
}