package com.example.rentapartment.dto;

import com.example.rentapartment.exception.LoginException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto extends LoginException {
    private String city;
    private String street;
    private String numberHouse;
    private String numberApartment;
    private ApartmentDto apartmentDto;


    @Override
    public String toString() {
        if(errorMessage!=null){
            return errorMessage;
        }
  return
        "AddressDto{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", numberHouse='" + numberHouse + '\'' +
                ", numberApartment='" + numberApartment + '\'' +
                ", apartmentDto=" + apartmentDto +
                '}';
    }

}
