package com.example.rentapartment.test_configuration;

import com.example.rentapartment.dto.Components;
import com.example.rentapartment.dto.GeacoderListValue;
import com.example.rentapartment.dto.GeacoderResponseDto;

import java.util.ArrayList;
import java.util.List;

public class TestConfig {

    public static final String LAT = "53.2007";

    public static final String LON = "45.0046";
    public static final String LAT_NAME = "latitude";
    public static final String LON_NAME = "longitude";

    public static GeacoderResponseDto prepaireGeacoderObject() {
        Components components = new Components("Penza", null);
        GeacoderListValue geacoderListValue = new GeacoderListValue(components);
        List<GeacoderListValue> listValue = new ArrayList<>();
        listValue.add(geacoderListValue);
        GeacoderResponseDto geacoderResponseDto = new GeacoderResponseDto(listValue);
        return geacoderResponseDto;

    }

}
