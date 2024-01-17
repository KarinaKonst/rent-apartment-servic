package com.example.rentapartment.repository;

import com.example.rentapartment.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity,Long> {

    ClientEntity getClientEntityByEmailAndPassword(String email,String password);
    ClientEntity getClientEntityByEmail(String email);
    ClientEntity getClientEntityBySessionToken(String authToken);
    List<ClientEntity> getClientEntitiesBySessionTokenIsNotNull();




}
