package com.example.rentapartment.repository;

import com.example.rentapartment.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

    @Query("SELECT a FROM AddressEntity a where a.street= ?1")
    List<AddressEntity> getAddressEntitiesListOnTheStreet(String street);

    @Query("SELECT a FROM AddressEntity a where a.city=?1")
    List<AddressEntity> getAddressEntitiesListOnTheCity(String city);



}