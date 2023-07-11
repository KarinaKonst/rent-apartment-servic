package com.example.rentproduct.repository;

import com.example.rentproduct.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionProductRepository extends JpaRepository<ProductEntity,Long> {
}
