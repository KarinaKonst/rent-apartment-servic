package com.example.interiorclient.model;

import com.example.interiorclient.dto.ApartmentDto;
import lombok.Data;

@Data
public class IntegrationModelToMailSendler {
    private String firstName;
    private String lastName;
    private String numberPassport;
    private String numberPhone;
    private String email;
    private String parentCity;
    private  Integer countOfGrocery;
    private String city;
    private String street;
    private String numberHouse;
    private String numberApartment;
    private ApartmentDto apartmentDto;
}
