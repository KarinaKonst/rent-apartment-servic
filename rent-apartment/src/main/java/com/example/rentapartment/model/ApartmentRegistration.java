package com.example.rentapartment.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApartmentRegistration {

    private String street;
    private String city;
    private String numberHouse;
    private String numberApartment;
    private String numberOfRooms;
    private String price;


}
