package com.example.rentapartment.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RenterDto {
    private String renterFullName;
    private String numberPhone;
    private String renterRating;

    public void setRenterFullName(String firstName, String lastName) {
        this.renterFullName = firstName + " " + lastName;
    }

}
