package com.example.rentapartment.service;

import com.example.rentapartment.entity.*;
import com.example.rentapartment.mapper.FullMapper;
import com.example.rentapartment.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EnableScheduling
@Service
public class SchedulerService {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerService.class);

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private BookingHistoryRepository bookingHistoryRepository;

    @Autowired
    private TotalRatingRepository totalRatingRepository;


    @Scheduled(fixedDelay = 60000)
    public void checkValidUserToken() {
        logger.info("rent-apartment :checkValidUserToken -> started");
        List<ClientEntity> clientEntities = clientRepository.getClientEntitiesBySessionTokenIsNotNull();
        for (ClientEntity a : clientEntities) {
            if (parsingTokenString(a.getSessionToken()).isBefore(LocalDateTime.now())) {
                a.setSessionToken(null);
                clientRepository.save(a);
                logger.info("rent-apartment :checkValidUserToken -> Удален просроченный токен у пользователя: " +
                        a.getEmail());
            }
        }
        logger.info("rent-apartment :checkValidUserToken -> end");
    }

    @Scheduled(fixedDelay = 60000)
    public void checkAvailabilityApartment() {
        List<ApartmentEntity> apartmentEntities = apartmentRepository.getApartmentEntitiesByAvailabilityIsFalse();
        for (ApartmentEntity a : apartmentEntities) {
//            Long id = a.getId();
            List<BookingHistoryEntity> bookingHistoryEntities = bookingHistoryRepository.getBookingHistoryEntitiesByApartmentId(a);
            for (BookingHistoryEntity b : bookingHistoryEntities) {
                if (b.getDateEnd().isBefore(LocalDate.now())) {
                    a.setAvailability(true);
                    apartmentRepository.save(a);
                }
            }
        }


    }

//    @Scheduled(fixedDelay = 60000)
    public void checkRatingApartment() {
        List<ApartmentEntity> apartmentEntitiesWithRating = apartmentRepository.getApartmentEntitiesByRaitingEntityListIsNotNull();
//        List<ApartmentEntity> apartmentEntitiesWithRating=new ArrayList<>();
        for (ApartmentEntity a : apartmentEntitiesWithRating) {
            Integer averageValueRaiting = getAverageValueRaiting(a.getRaitingEntityList());
            TotalRatingEntity totalRating = new TotalRatingEntity(averageValueRaiting, a);
            totalRatingRepository.deleteTotalRatingEntityByApartmentEntity(a);
                totalRatingRepository.save(totalRating);

        }
    }

    public Integer getAverageValueRaiting(List<RaitingEntity> result) {
        int sum = 0;
        for (RaitingEntity a : result) {
            sum = sum + a.getRating();
        }
        int averageRaiting = sum / result.size();

        return (averageRaiting);
    }

    private LocalDateTime parsingTokenString(String s) {
        int index = s.indexOf("|");
        String substring = s.substring(index + 1);
        return LocalDateTime.parse(substring);
    }

}
