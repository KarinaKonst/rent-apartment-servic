package com.example.rentapartment.repository;

import com.example.rentapartment.entity.ApiKeyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKeyEntity,Long> {
    ApiKeyEntity getApiKeyEntityByName(String name);
}
