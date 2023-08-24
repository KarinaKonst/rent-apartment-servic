package com.example.rentproduct.model;

import com.example.rentproduct.dto.ApartmentDto;
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
