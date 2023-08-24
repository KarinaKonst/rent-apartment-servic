package com.example.rentapartment.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InfoByCityDto {
    private String city;
    private String street;
    private String numberHouse;
}
