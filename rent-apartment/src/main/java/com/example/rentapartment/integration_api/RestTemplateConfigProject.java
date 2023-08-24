package com.example.rentapartment.integration_api;

import com.example.rentapartment.repository.ApiKeyRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RestTemplateConfigProject {

    @Autowired
    private ApiKeyRepository apiKeyRepository;

    public final static String INTEGRATION_URL = "https://%s/geocode/v1/json?q=%s+%s&no_annotations=1&key=%s";
    public  final  static String OPEN_CAGE_API="api.opencagedata.com";

    public void setAPI_KEY(String name) {
        this.API_KEY = apiKeyRepository.getApiKeyEntityByName(name).getValue();
    }

    public String API_KEY;


}
