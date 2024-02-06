package com.example.rentapartment.security_model;

import lombok.Data;

@Data
public class UserAuthorizationInfo {
    private String email;
    private  String password;

    public UserAuthorizationInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
