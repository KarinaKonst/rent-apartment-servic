package com.example.rentproduct.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApartmentDto {
    private String numberOfRooms;
    private String price;
    private Integer rating;
    private AddressDto addressDto;

}
