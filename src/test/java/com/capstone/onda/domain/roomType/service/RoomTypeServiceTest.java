package com.capstone.onda.domain.roomType.service;

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
import com.capstone.onda.domain.roomType.dto.response.RoomTypeEditResponse;
import com.capstone.onda.domain.roomType.dto.response.RoomTypeListResponse;
import com.capstone.onda.domain.roomType.dto.response.RoomTypeResponse;
import com.capstone.onda.domain.roomType.entity.RoomType;
import com.capstone.onda.domain.roomType.enumeration.amenity.AmenityOption;
import com.capstone.onda.domain.roomType.enumeration.attraction.AttractionOption;
import com.capstone.onda.domain.roomType.enumeration.facility.FacilityOption;
import com.capstone.onda.domain.roomType.enumeration.roomType.RoomTypeCategory;
import com.capstone.onda.domain.roomType.enumeration.service.ServiceOption;
import com.capstone.onda.domain.roomType.repository.RoomTypeRepository;
import com.capstone.onda.global.exception.ErrorCode;
import com.capstone.onda.global.security.dto.SecurityUser;
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
    void test1() {
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

        // when
        roomTypeService.postRoomType(securityUser, request);

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
    @DisplayName("객실타입 1개 조회")
    void test2() {
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

        RoomType savedRoomType = roomTypeRepository.save(roomType);


        // when
        RoomTypeResponse response = roomTypeService.getOneRoomType(securityUser, savedRoomType.getId());

        // then
        assertNotNull(response);
        assertEquals(1L, roomTypeRepository.count());
        assertEquals(hotel.getId(), response.getHotelId());
        assertEquals(savedRoomType.getId(), response.getRoomTypeId());
        assertEquals(RoomTypeCategory.ONDOL, response.getRoomTypeName());
        assertEquals(5, response.getTotalRoom());
        assertTrue(response.getFacilityOptions().containsAll(Arrays.asList(FacilityOption.BANQUET_HALL, FacilityOption.FITNESS)));
        assertTrue(response.getAttractionOptions().contains(AttractionOption.FISHING));
        assertTrue(response.getServiceOptions().containsAll(Arrays.asList(ServiceOption.CAMPFIRE, ServiceOption.BAR)));
        assertTrue(response.getAmenityOptions().containsAll(Arrays.asList(AmenityOption.AIR_CONDITIONER, AmenityOption.BIDET)));

    }

    @Test
    @DisplayName("객실타입 여러개 조회")
    void test3() {
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

        RoomType savedRoomType1 = roomTypeRepository.save(roomType1);

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

        RoomType savedRoomType2 = roomTypeRepository.save(roomType2);


        // when
        List<RoomTypeListResponse> roomTypes = roomTypeService.getListRoomType(securityUser);


        // then
        assertNotNull(roomTypes);
        assertEquals(2L, roomTypes.size());
        assertEquals(hotel.getId(), roomTypes.get(0).getHotelId());
        assertEquals(hotel.getId(), roomTypes.get(1).getHotelId());
        assertEquals(savedRoomType1.getId(), roomTypes.get(0).getRoomTypeId());
        assertEquals(savedRoomType2.getId(), roomTypes.get(1).getRoomTypeId());
        assertEquals(RoomTypeCategory.ONDOL, roomTypes.get(0).getRoomTypeName());
        assertEquals(RoomTypeCategory.DOUBLE, roomTypes.get(1).getRoomTypeName());
        assertEquals(5, roomTypes.get(0).getTotalRoom());
        assertEquals(3, roomTypes.get(1).getTotalRoom());
        assertTrue(roomTypes.get(0).getFacilityOptions().containsAll(Arrays.asList(FacilityOption.BANQUET_HALL, FacilityOption.FITNESS)));
        assertTrue(roomTypes.get(0).getAttractionOptions().contains(AttractionOption.FISHING));
        assertTrue(roomTypes.get(0).getServiceOptions().containsAll(Arrays.asList(ServiceOption.CAMPFIRE, ServiceOption.BAR)));
        assertTrue(roomTypes.get(0).getAmenityOptions().containsAll(Arrays.asList(AmenityOption.AIR_CONDITIONER, AmenityOption.BIDET)));
        assertTrue(roomTypes.get(1).getFacilityOptions().containsAll(Arrays.asList(FacilityOption.BASKETBALL_COURT, FacilityOption.CHILDCARE)));
        assertTrue(roomTypes.get(1).getAttractionOptions().contains(AttractionOption.SKI));
        assertTrue(roomTypes.get(1).getServiceOptions().containsAll(Arrays.asList(ServiceOption.BOARD_GAME, ServiceOption.BREAKFAST)));
        assertTrue(roomTypes.get(1).getAmenityOptions().containsAll(Arrays.asList(AmenityOption.PARTY_ROOM, AmenityOption.TABLE)));
    }


    @Test
    @DisplayName("객실타입 수정")
    void test4() {
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

        RoomType savedRoomType = roomTypeRepository.save(roomType);

        RoomTypeEdit roomTypeEdit = RoomTypeEdit.builder()
                .facilityOptions(List.of(FacilityOption.FITNESS))
                .attractionOptions(List.of(AttractionOption.FISHING, AttractionOption.GOLF))
                .serviceOptions(List.of(ServiceOption.CAMPFIRE, ServiceOption.BAR))
                .amenityOptions(List.of(AmenityOption.AIR_CONDITIONER))
                .build();

        // when
        RoomTypeEditResponse roomTypeEditResponse = roomTypeService.editRoomType(securityUser, savedRoomType.getId(), roomTypeEdit);

        // then
        assertNotNull(roomTypeEditResponse);
        assertEquals(hotel.getId(), roomTypeEditResponse.getHotelId());
        assertEquals(savedRoomType.getId(), roomTypeEditResponse.getRoomTypeId());
        assertEquals(RoomTypeCategory.ONDOL, roomTypeEditResponse.getRoomTypeName());
        assertEquals(5, roomTypeEditResponse.getTotalRoom());
        assertTrue(roomTypeEditResponse.getFacilityOptions().contains(FacilityOption.FITNESS));
        assertTrue(roomTypeEditResponse.getAttractionOptions().containsAll(Arrays.asList(AttractionOption.FISHING, AttractionOption.GOLF)));
        assertTrue(roomTypeEditResponse.getServiceOptions().containsAll(Arrays.asList(ServiceOption.CAMPFIRE, ServiceOption.BAR)));
        assertTrue(roomTypeEditResponse.getAmenityOptions().contains(AmenityOption.AIR_CONDITIONER));

    }


    @Test
    @DisplayName("객실타입 삭제")
    void test5() {
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


        // when
        roomTypeService.deleteRoomType(securityUser, roomType.getId());

        // then
        assertEquals(0, roomTypeRepository.count());
    }
}