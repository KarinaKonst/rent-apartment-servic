package com.example.rentproduct.service.impl;

import com.example.rentproduct.dto.AddressDto;
import com.example.rentproduct.dto.ClientDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RentProductSeviceImpl {

    public static final String BASE_URL = "http://localhost:8085/api/choice-apartment";
    RestTemplate restTemplate = new RestTemplate();


    public String throwSendlerOnRentApartmentService(ClientDto clientInfo, AddressDto addressDto) {

        String body = restTemplate.exchange(
                BASE_URL,
                HttpMethod.POST,
                new HttpEntity<>(null, null),
                String.class).getBody();
        return body;
    }
}
