package com.example.interiorclient.repository;

import com.example.interiorclient.entity.SmtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmtpRepository extends JpaRepository<SmtpEntity,Long> {
}
