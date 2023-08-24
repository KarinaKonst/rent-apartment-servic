package com.example.rentapartment.repository;


import com.example.rentapartment.entity.ApartmentEntity;
import com.example.rentapartment.entity.RaitingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaitingRepository extends JpaRepository<RaitingEntity,Long> {
    @Query("SELECT r FROM RaitingEntity  r WHERE r.apartment= :id")
    public List<RaitingEntity> getRaiting(ApartmentEntity id);

    public  List<RaitingEntity> findRaitingEntitiesByApartment_Id(Long id);


}
