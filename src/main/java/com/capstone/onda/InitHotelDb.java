package com.capstone.onda;

import com.capstone.onda.domain.hotel.entity.Hotel;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitHotelDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Hotel hotel = Hotel.builder()
                    .hotelName("신라호텔")
                    .region("서울")
                    .build();

            em.persist(hotel);
        }
    }
}
