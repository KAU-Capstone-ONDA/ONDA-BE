package com.capstone.onda.domain.roomType.dto.response;

import com.capstone.onda.domain.roomType.entity.RoomType;
import com.capstone.onda.domain.roomType.enumeration.roomType.RoomTypeCategory;
import lombok.Builder;
import lombok.Data;




@Data
public class RoomTypeListResponse {
    private Long id;
    private RoomTypeCategory roomTypeName;
    private Integer totalRoom;


    public RoomTypeListResponse(RoomType roomType) {
        this.id = roomType.getId();
        this.roomTypeName = roomType.getRoomTypeCategory();
        this.totalRoom = roomType.getTotalRoom();
    }

    @Builder
    public RoomTypeListResponse(Long id, RoomTypeCategory roomTypeName, Integer totalRoom) {
        this.id = id;
        this.roomTypeName = roomTypeName;
        this.totalRoom = totalRoom;
    }
}
