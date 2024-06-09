package com.capstone.onda.domain.hotel.repository;


import com.capstone.onda.domain.hotel.entity.Hotel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("select h from Hotel h where replace(h.hotelName, ' ', '') like concat('%', :hotel_name, '%')")
    List<Hotel> findAllByHotelName(@Param("hotel_name") String hotelName);

}
