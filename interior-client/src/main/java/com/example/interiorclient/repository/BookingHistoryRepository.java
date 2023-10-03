package com.example.interiorclient.repository;

import com.example.interiorclient.entity.BookingHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingHistoryRepository extends JpaRepository<BookingHistoryEntity,Long > {
    @Query(nativeQuery = true, value = "SELECT * FROM booking_history ORDER BY id DESC LIMIT 1")
    Long getLastId();
}
