package com.capstone.onda.domain.roomType.controller;


import com.capstone.onda.domain.roomType.dto.request.RoomTypeEdit;
import com.capstone.onda.domain.roomType.dto.request.RoomTypeRequest;
import com.capstone.onda.domain.roomType.dto.response.RoomTypeListResponse;
import com.capstone.onda.domain.roomType.dto.response.RoomTypePostResponse;
import com.capstone.onda.domain.roomType.dto.response.RoomTypeResponse;
import com.capstone.onda.domain.roomType.service.RoomTypeService;
import com.capstone.onda.global.common.ResponseDTO;
import com.capstone.onda.global.security.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "roomType api", description = "roomType 관련 API")
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class RoomTypeController {

    private final RoomTypeService roomTypeService;
    private final String AUTHORIZATION_HEADER = "Authorization";

    @PostMapping("/hotel/room-types/create")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "객실타입 등록 API")
    public ResponseDTO<RoomTypePostResponse> postRoomType(
        @RequestBody @Valid RoomTypeRequest request) {
        return ResponseDTO.res(roomTypeService.postRoomType(SecurityUtil.getUser(), request),
            "객실타입 등록에 성공했습니다.");
    }

    @GetMapping("/room-types/{roomTypeId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "객실타입 단건 조회 API")
    public ResponseDTO<RoomTypeResponse> getOneRoomType(
        @RequestHeader(AUTHORIZATION_HEADER) final String accessToken,
        @PathVariable Long roomTypeId) {
        return ResponseDTO.res(roomTypeService.getOneRoomType(roomTypeId), "객실타입 조회에 성공했습니다.");
    }

    @GetMapping("/hotel/{hotelId}/room-types")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "객실타입 리스트 조회 API")
    public ResponseDTO<List<RoomTypeListResponse>> getListRoomType(
        @RequestHeader(AUTHORIZATION_HEADER) final String accessToken, @PathVariable Long hotelId) {
        return ResponseDTO.res(roomTypeService.getListRoomType(hotelId), "객실타입 리스트 조회에 성공했습니다.");
    }

    @PatchMapping("/room-types/{roomTypeId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "객실타입 수정 API")
    public ResponseDTO<String> editRoomType(
        @RequestHeader(AUTHORIZATION_HEADER) final String accessToken,
        @PathVariable Long roomTypeId, @RequestBody @Valid RoomTypeEdit request) {
        roomTypeService.editRoomType(roomTypeId, request);
        return ResponseDTO.res("객실타입 수정에 성공했습니다.");
    }

    @PostMapping("/room-types/{roomTypeId}/delete")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "객실타입 삭제 API")
    public ResponseDTO<String> deleteRoomType(@RequestHeader(AUTHORIZATION_HEADER) final String accessToken, @PathVariable Long roomTypeId) {
        roomTypeService.deleteRoomType(roomTypeId);
        return ResponseDTO.res("객실타입 삭제에 성공했습니다.");
    }

}