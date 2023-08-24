package com.example.rentapartment.integration_api.impl;

import com.example.rentapartment.integration_api.RestTemplateConfigProject;
import com.example.rentapartment.integration_api.RestTemplateManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.example.rentapartment.integration_api.RestTemplateConfigProject.INTEGRATION_URL;
import static com.example.rentapartment.integration_api.RestTemplateConfigProject.OPEN_CAGE_API;


@Service
public class RestTemplateManagerServiceImpl implements RestTemplateManagerService {
@Autowired
    private RestTemplateConfigProject conf;
    @Override
    public String searchCity(String latitude, String longitude)  {
        RestTemplate restTemplate = new RestTemplate();
        conf.setAPI_KEY(OPEN_CAGE_API);
        String body = restTemplate.exchange(String.format(INTEGRATION_URL,OPEN_CAGE_API, latitude, longitude,conf.getAPI_KEY()),
                HttpMethod.GET,
                new HttpEntity<>(null, null),
                String.class).getBody();

        return body;
    }
}
