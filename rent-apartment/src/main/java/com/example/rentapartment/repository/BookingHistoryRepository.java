package com.example.rentapartment.repository;

import com.example.rentapartment.entity.ApartmentEntity;
import com.example.rentapartment.entity.BookingHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingHistoryRepository extends JpaRepository<BookingHistoryEntity,Long > {
    @Query(nativeQuery = true, value = "SELECT * FROM booking_history ORDER BY id DESC LIMIT 1")
    Long getLastId();

    List<BookingHistoryEntity> getBookingHistoryEntitiesByApartmentId(ApartmentEntity apartmentEntity);
//    @Query(value = "SELECT b FROM BookingHistoryEntity b  WHERE b.apartmentId.id")

    List<BookingHistoryEntity> getBookingHistoryEntitiesByClientId(Long id);
//    @Query(value ="SELECT b FROM  BookingHistoryEntity  b WHERE  " )
    List<BookingHistoryEntity> getBookingHistoryEntitiesByDateStartIsAfter(LocalDate reportDate);
    List<BookingHistoryEntity> getBookingHistoryEntitiesByDateStartIsAfterAndApartmentId(LocalDate reportDate, ApartmentEntity apartmentEntity);
}


