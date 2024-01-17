package com.example.rentapartment.repository;

import com.example.rentapartment.entity.ApartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartmentRepository extends JpaRepository<ApartmentEntity, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM apartment_info ORDER BY id DESC LIMIT 1")
    Long getLastId();
    List<ApartmentEntity> getApartmentEntitiesByAvailabilityIsFalse();

    List<ApartmentEntity> getApartmentEntitiesByPriceAndNumberOfRooms(String price, String numberOfRooms);
    List<ApartmentEntity> getApartmentEntitiesByRaitingEntityListIsNotNull();


}
