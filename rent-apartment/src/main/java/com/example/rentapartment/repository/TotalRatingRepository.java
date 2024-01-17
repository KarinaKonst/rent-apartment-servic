package com.example.rentapartment.repository;

import com.example.rentapartment.entity.ApartmentEntity;
import com.example.rentapartment.entity.RaitingEntity;
import com.example.rentapartment.entity.TotalRatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TotalRatingRepository extends JpaRepository<TotalRatingEntity,Long> {
    public List<TotalRatingEntity> getTotalRatingEntitiesByTotalRatingIsNotNull();
    @Query("SELECT r FROM TotalRatingEntity  r WHERE r.totalRating is NOT NULL ")
    public List<TotalRatingEntity> getTotalRating();

    public void deleteTotalRatingEntityByApartmentEntity(ApartmentEntity apartmentEntity);
}
