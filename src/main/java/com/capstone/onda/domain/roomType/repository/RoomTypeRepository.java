package com.capstone.onda.domain.roomType.repository;


import com.capstone.onda.domain.roomType.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long>{

    List<RoomType> findByHotelId(Long hotelId);
}