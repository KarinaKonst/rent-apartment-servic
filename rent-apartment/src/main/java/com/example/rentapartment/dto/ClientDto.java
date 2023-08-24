package com.example.rentapartment.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class ClientDto {

    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String numberPassport;
    private String numberPhone;
    private String email;
    private String parentCity;
    private  Integer countOfGrocery;





}
