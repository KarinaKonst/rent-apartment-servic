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

    public ApartmentRegistration(String city,String street,String numberHouse, String numberApartment, String numberOfRooms,String price) {
        this.city = city;
        this.street=street;
        this.numberHouse=numberHouse;
        this.numberApartment=numberApartment;
        this.numberOfRooms=numberOfRooms;
        this.price=price;
    }




}
