package com.example.interiorclient.service;


import com.example.interiorclient.dto.AddressDto;
import com.example.interiorclient.dto.ClientDto;
import com.example.interiorclient.entity.BookingHistoryEntity;


public interface ProductService {
    public void throwInfoToProductService(Long id);
    public void sendMessage(Long id);
    public Long checkingForProductDistribution(ClientDto clientInfo, AddressDto addressDto, BookingHistoryEntity history);

}
