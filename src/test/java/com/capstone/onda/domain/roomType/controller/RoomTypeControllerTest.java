package com.capstone.onda.domain.roomType.controller;

import com.capstone.onda.domain.hotel.entity.Hotel;
import com.capstone.onda.domain.hotel.exception.HotelNotFound;
import com.capstone.onda.domain.hotel.repository.HotelRepository;
import com.capstone.onda.domain.member.dto.request.MemberSignUpRequest;
import com.capstone.onda.domain.member.entity.Member;
import com.capstone.onda.domain.member.exception.InvalidMemberException;
import com.capstone.onda.domain.member.repository.MemberRepository;
import com.capstone.onda.domain.member.service.MemberService;
import com.capstone.onda.domain.roomType.dto.request.RoomTypeEdit;
import com.capstone.onda.domain.roomType.dto.request.RoomTypeRequest;
import com.capstone.onda.domain.roomType.entity.RoomType;
import com.capstone.onda.domain.roomType.enumeration.amenity.AmenityOption;
import com.capstone.onda.domain.roomType.enumeration.attraction.AttractionOption;
import com.capstone.onda.domain.roomType.enumeration.facility.FacilityOption;
import com.capstone.onda.domain.roomType.enumeration.roomType.RoomTypeCategory;
import com.capstone.onda.domain.roomType.enumeration.service.ServiceOption;
import com.capstone.onda.domain.roomType.repository.RoomTypeRepository;
import com.capstone.onda.global.exception.ErrorCode;
import com.capstone.onda.global.security.dto.SecurityUser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;


    @BeforeEach
    void clean() {
        roomTypeRepository.deleteAll();
        hotelRepository.deleteAll();
        memberRepository.deleteAll();
    }


    @Test
    @DisplayName("객실타입 등록")
    void test1() throws Exception {
        // given
        SecurityUser securityUser = SecurityUser.builder()
                .email("rkdeo4104@naver.com")
                .nickname("대양")
                .role("관리자")
                .build();

        Member member = memberRepository.findByUserEmail(securityUser.email()).orElseThrow(
                () -> new InvalidMemberException(ErrorCode.INVALID_MEMBER_EXCEPTION));

        memberRepository.save(member);

        MemberSignUpRequest memberSignUpRequest = MemberSignUpRequest.builder()
                .email("rkdeo4104@naver.com")
                .name("대양")
                .provider("카카오")
                .hotelName("신라호텔")
                .region("서울")
                .city("마포구")
                .build();

        memberService.signUp(memberSignUpRequest);

        Hotel hotel = hotelRepository.findById(member.getHotel().getId())
                .orElseThrow(() -> new HotelNotFound(ErrorCode.INVALID_HOTEL_EXCEPTION));

        RoomTypeRequest request = RoomTypeRequest.builder()
                .roomTypeName(RoomTypeCategory.ONDOL)
                .totalRoom(5)
                .facilityOptions(List.of(FacilityOption.BANQUET_HALL, FacilityOption.FITNESS))
                .attractionOptions(List.of(AttractionOption.FISHING))
                .serviceOptions(List.of())
                .amenityGroups(List.of())
                .amenityOptions(List.of())
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/v1/hotel/room-types/create")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        assertEquals(1L, memberRepository.count());
        assertEquals(1L, hotelRepository.count());
        assertEquals(1L, roomTypeRepository.count());
        assertEquals("rkdeo4104@naver.com", member.getUserEmail());
        assertEquals("대양", member.getUserNickName());
        assertEquals("신라호텔", hotel.getHotelName());
        assertEquals("서울", hotel.getRegion());
        assertEquals("마포구", hotel.getCity());

        RoomType roomType = roomTypeRepository.findAll().get(0);
        assertEquals(RoomTypeCategory.ONDOL, roomType.getRoomTypeCategory());
        assertEquals(5, roomType.getTotalRoom());
        assertTrue(roomType.getFacilityOptions().containsAll(Arrays.asList(FacilityOption.BANQUET_HALL, FacilityOption.FITNESS)));
        assertTrue(roomType.getAttractionOptions().contains(AttractionOption.FISHING));
        assertEquals("FISHING", roomType.getAttractionOptions().get(0).toString());
        assertEquals(AttractionOption.FISHING, roomType.getAttractionOptions().get(0));

    }

    @Test
    @DisplayName("객실타입 단건 조회")
    void test2() throws Exception {
        // given
        SecurityUser securityUser = SecurityUser.builder()
                .email("rkdeo4104@naver.com")
                .nickname("대양")
                .role("관리자")
                .build();

        Member member = memberRepository.findByUserEmail(securityUser.email()).orElseThrow(
                () -> new InvalidMemberException(ErrorCode.INVALID_MEMBER_EXCEPTION));

        memberRepository.save(member);

        MemberSignUpRequest memberSignUpRequest = MemberSignUpRequest.builder()
                .email("rkdeo4104@naver.com")
                .name("대양")
                .provider("카카오")
                .hotelName("신라호텔")
                .region("서울")
                .city("마포구")
                .build();

        memberService.signUp(memberSignUpRequest);

        Hotel hotel = hotelRepository.findById(member.getHotel().getId())
                .orElseThrow(() -> new HotelNotFound(ErrorCode.INVALID_HOTEL_EXCEPTION));

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

        hotel.addRoomType(roomType);

        roomTypeRepository.save(roomType);

        // expected
        mockMvc.perform(get("/v1/room-types/{roomTypeId}", roomType.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.hotelId").value(roomType.getHotel().getId()))
                .andExpect(jsonPath("$.data.roomTypeId").value(roomType.getId()))
                .andExpect(jsonPath("$.data.roomTypeName").value(roomType.getRoomTypeCategory().toString()))
                .andExpect(jsonPath("$.data.totalRoom").value(roomType.getTotalRoom()))
                .andExpect(jsonPath("$.data.facilityOptions[0]").value(roomType.getFacilityOptions().get(0).toString()))
                .andExpect(jsonPath("$.data.facilityOptions[1]").value(roomType.getFacilityOptions().get(1).toString()))
                .andExpect(jsonPath("$.data.attractionOptions[0]").value(roomType.getAttractionOptions().get(0).toString()))
                .andExpect(jsonPath("$.data.serviceOptions[0]").value(roomType.getServiceOptions().get(0).toString()))
                .andExpect(jsonPath("$.data.serviceOptions[1]").value(roomType.getServiceOptions().get(1).toString()))
                .andExpect(jsonPath("$.data.amenityOptions[0]").value(roomType.getAmenityOptions().get(0).toString()))
                .andExpect(jsonPath("$.data.amenityOptions[1]").value(roomType.getAmenityOptions().get(1).toString()))
                .andDo(print());
    }

    @Test
    @DisplayName("객실타입 리스트 조회")
    void test3() throws Exception {
        // given
        SecurityUser securityUser = SecurityUser.builder()
                .email("rkdeo4104@naver.com")
                .nickname("대양")
                .role("관리자")
                .build();

        Member member = memberRepository.findByUserEmail(securityUser.email()).orElseThrow(
                () -> new InvalidMemberException(ErrorCode.INVALID_MEMBER_EXCEPTION));

        memberRepository.save(member);

        MemberSignUpRequest memberSignUpRequest = MemberSignUpRequest.builder()
                .email("rkdeo4104@naver.com")
                .name("대양")
                .provider("카카오")
                .hotelName("신라호텔")
                .region("서울")
                .city("마포구")
                .build();

        memberService.signUp(memberSignUpRequest);

        Hotel hotel = hotelRepository.findById(member.getHotel().getId())
                .orElseThrow(() -> new HotelNotFound(ErrorCode.INVALID_HOTEL_EXCEPTION));

        RoomType roomType1 = RoomType.builder()
                .roomTypeCategory(RoomTypeCategory.ONDOL)
                .totalRoom(5)
                .build();

        roomType1.addFacilityOption(FacilityOption.BANQUET_HALL);
        roomType1.addFacilityOption(FacilityOption.FITNESS);
        roomType1.addAttractionOption(AttractionOption.FISHING);
        roomType1.addServiceOption(ServiceOption.CAMPFIRE);
        roomType1.addServiceOption(ServiceOption.BAR);
        roomType1.addAmenityOption(AmenityOption.AIR_CONDITIONER);
        roomType1.addAmenityOption(AmenityOption.BIDET);

        hotel.addRoomType(roomType1);

        roomTypeRepository.save(roomType1);

        RoomType roomType2 = RoomType.builder()
                .roomTypeCategory(RoomTypeCategory.DOUBLE)
                .totalRoom(3)
                .build();

        roomType2.addFacilityOption(FacilityOption.BASKETBALL_COURT);
        roomType2.addFacilityOption(FacilityOption.CHILDCARE);
        roomType2.addAttractionOption(AttractionOption.SKI);
        roomType2.addServiceOption(ServiceOption.BOARD_GAME);
        roomType2.addServiceOption(ServiceOption.BREAKFAST);
        roomType2.addAmenityOption(AmenityOption.PARTY_ROOM);
        roomType2.addAmenityOption(AmenityOption.TABLE);

        hotel.addRoomType(roomType2);

        roomTypeRepository.save(roomType2);


        // expected
        mockMvc.perform(get("/v1/hotel/room-types")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$.data.[0].hotelId").value(hotel.getRoomTypes().get(0).getHotel().getId()))
                .andExpect(jsonPath("$.data.[0].roomTypeId").value(hotel.getRoomTypes().get(0).getId()))
                .andExpect(jsonPath("$.data.[0].roomTypeName").value(hotel.getRoomTypes().get(0).getRoomTypeCategory().toString()))
                .andExpect(jsonPath("$.data.[0].totalRoom").value(hotel.getRoomTypes().get(0).getTotalRoom()))
                .andExpect(jsonPath("$.data.[0].facilityOptions[0]").value(hotel.getRoomTypes().get(0).getFacilityOptions().get(0).toString()))
                .andExpect(jsonPath("$.data.[0].facilityOptions[1]").value(hotel.getRoomTypes().get(0).getFacilityOptions().get(1).toString()))
                .andExpect(jsonPath("$.data.[0].attractionOptions[0]").value(hotel.getRoomTypes().get(0).getAttractionOptions().get(0).toString()))
                .andExpect(jsonPath("$.data.[0].serviceOptions[0]").value(hotel.getRoomTypes().get(0).getServiceOptions().get(0).toString()))
                .andExpect(jsonPath("$.data.[0].serviceOptions[1]").value(hotel.getRoomTypes().get(0).getServiceOptions().get(1).toString()))
                .andExpect(jsonPath("$.data.[0].amenityOptions[0]").value(hotel.getRoomTypes().get(0).getAmenityOptions().get(0).toString()))
                .andExpect(jsonPath("$.data.[0].amenityOptions[1]").value(hotel.getRoomTypes().get(0).getAmenityOptions().get(1).toString()))
                .andExpect(jsonPath("$.data.[1].hotelId").value(hotel.getRoomTypes().get(1).getHotel().getId()))
                .andExpect(jsonPath("$.data.[1].roomTypeId").value(hotel.getRoomTypes().get(1).getId()))
                .andExpect(jsonPath("$.data.[1].roomTypeName").value(hotel.getRoomTypes().get(1).getRoomTypeCategory().toString()))
                .andExpect(jsonPath("$.data.[1].totalRoom").value(hotel.getRoomTypes().get(1).getTotalRoom()))
                .andExpect(jsonPath("$.data.[1].facilityOptions[0]").value(hotel.getRoomTypes().get(1).getFacilityOptions().get(0).toString()))
                .andExpect(jsonPath("$.data.[1].facilityOptions[1]").value(hotel.getRoomTypes().get(1).getFacilityOptions().get(1).toString()))
                .andExpect(jsonPath("$.data.[1].attractionOptions[0]").value(hotel.getRoomTypes().get(1).getAttractionOptions().get(0).toString()))
                .andExpect(jsonPath("$.data.[1].serviceOptions[0]").value(hotel.getRoomTypes().get(1).getServiceOptions().get(0).toString()))
                .andExpect(jsonPath("$.data.[1].serviceOptions[1]").value(hotel.getRoomTypes().get(1).getServiceOptions().get(1).toString()))
                .andExpect(jsonPath("$.data.[1].amenityOptions[0]").value(hotel.getRoomTypes().get(1).getAmenityOptions().get(0).toString()))
                .andExpect(jsonPath("$.data.[1].amenityOptions[1]").value(hotel.getRoomTypes().get(1).getAmenityOptions().get(1).toString()))
                .andDo(print());
    }


    @Test
    @DisplayName("객실타입 수정")
    void test4() throws Exception {
        // given
        SecurityUser securityUser = SecurityUser.builder()
                .email("rkdeo4104@naver.com")
                .nickname("대양")
                .role("관리자")
                .build();

        Member member = memberRepository.findByUserEmail(securityUser.email()).orElseThrow(
                () -> new InvalidMemberException(ErrorCode.INVALID_MEMBER_EXCEPTION));

        memberRepository.save(member);

        MemberSignUpRequest memberSignUpRequest = MemberSignUpRequest.builder()
                .email("rkdeo4104@naver.com")
                .name("대양")
                .provider("카카오")
                .hotelName("신라호텔")
                .region("서울")
                .city("마포구")
                .build();

        memberService.signUp(memberSignUpRequest);

        Hotel hotel = hotelRepository.findById(member.getHotel().getId())
                .orElseThrow(() -> new HotelNotFound(ErrorCode.INVALID_HOTEL_EXCEPTION));

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

        hotel.addRoomType(roomType);

        roomTypeRepository.save(roomType);

        RoomTypeEdit roomTypeEdit = RoomTypeEdit.builder()
                .facilityOptions(List.of(FacilityOption.FITNESS))
                .attractionOptions(List.of(AttractionOption.FISHING, AttractionOption.GOLF))
                .serviceOptions(List.of(ServiceOption.CAMPFIRE, ServiceOption.BAR))
                .amenityOptions(List.of(AmenityOption.AIR_CONDITIONER))
                .build();

        // expected
        mockMvc.perform(patch("/v1/room-types/{roomTypeId}", roomType.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomTypeEdit)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.hotelId").value(roomType.getHotel().getId()))
                .andExpect(jsonPath("$.data.roomTypeId").value(roomType.getId()))
                .andExpect(jsonPath("$.data.roomTypeName").value(roomType.getRoomTypeCategory().toString()))
                .andExpect(jsonPath("$.data.totalRoom").value(roomType.getTotalRoom()))
                .andExpect(jsonPath("$.data.facilityOptions[0]").value(roomType.getFacilityOptions().get(0).toString()))
                .andExpect(jsonPath("$.data.attractionOptions[0]").value(roomType.getAttractionOptions().get(0).toString()))
                .andExpect(jsonPath("$.data.attractionOptions[1]").value(roomType.getAttractionOptions().get(1).toString()))
                .andExpect(jsonPath("$.data.serviceOptions[0]").value(roomType.getServiceOptions().get(0).toString()))
                .andExpect(jsonPath("$.data.serviceOptions[1]").value(roomType.getServiceOptions().get(1).toString()))
                .andExpect(jsonPath("$.data.amenityOptions[0]").value(roomType.getAmenityOptions().get(0).toString()))
                .andDo(print());
    }


    @Test
    @DisplayName("객실타입 삭제")
    void test5() throws Exception {
        // given
        SecurityUser securityUser = SecurityUser.builder()
                .email("rkdeo4104@naver.com")
                .nickname("대양")
                .role("관리자")
                .build();

        Member member = memberRepository.findByUserEmail(securityUser.email()).orElseThrow(
                () -> new InvalidMemberException(ErrorCode.INVALID_MEMBER_EXCEPTION));

        memberRepository.save(member);

        MemberSignUpRequest memberSignUpRequest = MemberSignUpRequest.builder()
                .email("rkdeo4104@naver.com")
                .name("대양")
                .provider("카카오")
                .hotelName("신라호텔")
                .region("서울")
                .city("마포구")
                .build();

        memberService.signUp(memberSignUpRequest);

        Hotel hotel = hotelRepository.findById(member.getHotel().getId())
                .orElseThrow(() -> new HotelNotFound(ErrorCode.INVALID_HOTEL_EXCEPTION));

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

        hotel.addRoomType(roomType);

        roomTypeRepository.save(roomType);

        // expected
        mockMvc.perform(post("/v1/room-types/{roomTypeId}/delete", roomType.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
