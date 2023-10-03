package com.example.interiorclient.repository;

import com.example.interiorclient.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionProductRepository extends JpaRepository<ProductEntity,Long> {
}
