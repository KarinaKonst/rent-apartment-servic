package com.example.rentapartment.security_model;

import lombok.Data;

@Data
public class UserAuthorizationInfo {
    private String email;
    private  String password;
}
