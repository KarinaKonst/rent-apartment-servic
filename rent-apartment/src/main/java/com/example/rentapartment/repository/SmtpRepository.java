package com.example.rentapartment.repository;


import com.example.rentapartment.entity.SmtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmtpRepository extends JpaRepository<SmtpEntity,Long> {
}
