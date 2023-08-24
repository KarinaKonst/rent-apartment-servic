package com.example.rentapartment.repository;

import com.example.rentapartment.entity.ClientEntity;
import com.example.rentapartment.entity.RenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RenterRepository extends JpaRepository<RenterEntity,Long> {
    RenterEntity getRenterEntityByEmail(String email);
}
