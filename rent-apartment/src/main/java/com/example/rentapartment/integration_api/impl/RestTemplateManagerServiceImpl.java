package com.example.rentapartment.integration_api.impl;

import com.example.rentapartment.dto.GeacoderListValue;
import com.example.rentapartment.dto.GeacoderResponseDto;
import com.example.rentapartment.integration_api.RestTemplateConfigProject;
import com.example.rentapartment.integration_api.RestTemplateManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.example.rentapartment.integration_api.RestTemplateConfigProject.INTEGRATION_URL;
import static com.example.rentapartment.integration_api.RestTemplateConfigProject.OPEN_CAGE_API;


@Service
public class RestTemplateManagerServiceImpl implements RestTemplateManagerService {
    @Autowired
    private RestTemplateConfigProject conf;

    @Override
    public GeacoderResponseDto searchCity(String latitude, String longitude) {
        RestTemplate restTemplate = new RestTemplate();
        conf.setAPI_KEY(OPEN_CAGE_API);
        GeacoderResponseDto body = restTemplate.exchange(String.format(INTEGRATION_URL, OPEN_CAGE_API, latitude, longitude, conf.getAPI_KEY()),
                HttpMethod.GET,
                new HttpEntity<>(null, null),
                GeacoderResponseDto.class).getBody();

        return body;
    }
}
