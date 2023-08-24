package com.example.rentapartment.repository;


import com.example.rentapartment.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionProductRepository extends JpaRepository<ProductEntity,Long > {
}
