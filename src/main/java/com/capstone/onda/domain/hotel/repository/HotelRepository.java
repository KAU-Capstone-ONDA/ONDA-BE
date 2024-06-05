package com.capstone.onda.domain.hotel.repository;



import com.capstone.onda.domain.hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
