package com.example.rentapartment.service;

public interface ValidateUserToken {
    public void checkValidateSession(String authToken) ;
    public String getEmailSession(String authToken);
}
