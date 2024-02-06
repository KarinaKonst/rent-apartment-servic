package com.example.rentapartment.test_configuration;

import com.example.rentapartment.dto.Components;
import com.example.rentapartment.dto.GeacoderListValue;
import com.example.rentapartment.dto.GeacoderResponseDto;
import com.example.rentapartment.model.ApartmentRegistration;
import com.example.rentapartment.model.FeedbackModel;
import com.example.rentapartment.security_model.UserAuthorizationInfo;
import com.example.rentapartment.security_model.UserRegistrationInfo;

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

    public static FeedbackModel getFeedbackModel() {
        return new FeedbackModel("Отлично", 5, 11L);
    }

    public static ApartmentRegistration getApartmentRegistration() {
        return new ApartmentRegistration("Москва",
                "Строителей",
                "3",
                "17",
                "2",
                "3500");
    }
    public static UserRegistrationInfo getUserRegistration(){
        return  new UserRegistrationInfo("Иванов",
                "Иван",
                "5617 250798",
                "+79980923415",
                "Москва",
                "ivanovi@yandex.ru",
                "Ivanov!");
    }
    public static UserAuthorizationInfo getUserAuthorization(){
        return new UserAuthorizationInfo("adminTest","adminTest");
    }


}
