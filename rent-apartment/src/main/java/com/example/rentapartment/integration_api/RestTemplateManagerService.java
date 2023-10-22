package com.example.rentapartment.integration_api;

import com.example.rentapartment.dto.GeacoderResponseDto;

public interface RestTemplateManagerService {
    public GeacoderResponseDto searchCity(String latitude , String  longitude) ;
}
