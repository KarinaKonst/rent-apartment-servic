package com.example.rentproduct.service;

import com.example.rentproduct.dto.AddressDto;
import com.example.rentproduct.dto.ClientDto;
import com.example.rentproduct.entity.BookingHistoryEntity;

import java.time.LocalDateTime;

public interface ProductService {
    public void throwInfoToProductService(Long id);
    public Long checkingForProductDistribution(ClientDto clientInfo, AddressDto addressDto, BookingHistoryEntity history);

}
