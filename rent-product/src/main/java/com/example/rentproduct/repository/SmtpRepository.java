package com.example.rentproduct.repository;

import com.example.rentproduct.entity.SmtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmtpRepository extends JpaRepository<SmtpEntity,Long> {
}
