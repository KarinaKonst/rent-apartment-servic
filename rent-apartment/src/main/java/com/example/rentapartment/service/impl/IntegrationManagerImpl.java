package com.example.rentapartment.service.impl;

import com.example.rentapartment.service.IntegrationManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.example.rentapartment.constant.ConstantProject.BASE_URL;

@Service
public class IntegrationManagerImpl implements IntegrationManager {

    RestTemplate restTemplate = new RestTemplate();

    @Override
    public String throwInfoOnRentProduct(Long id) {

        String body = restTemplate.exchange(
                String.format(BASE_URL,id),
                HttpMethod.POST,
                new HttpEntity<>(null, null),
                String.class).getBody();
        return body;
    }


}
