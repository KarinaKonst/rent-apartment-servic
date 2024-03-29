package com.example.rentapartment.security_model;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@Data
public class UserRegistrationInfo {
    private String firstName;

    private String lastName;

    private String numberPassport;

    private String numberPhone;

    private String parentCity;

    private String email;

    private String password;
    private LocalDate birthday;

    public UserRegistrationInfo(String firstName, String lastName, String numberPassport, String numberPhone, String parentCity, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.numberPassport = numberPassport;
        this.numberPhone = numberPhone;
        this.parentCity = parentCity;
        this.email = email;
        this.password = password;

    }
}
