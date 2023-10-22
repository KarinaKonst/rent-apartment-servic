package com.example.rentapartment.service;

import com.example.rentapartment.model.ResponseInfo;
import com.example.rentapartment.security_model.UserAuthorizationInfo;
import com.example.rentapartment.security_model.UserRegistrationInfo;

public interface ClientRegistrationService {

    public String registrationUser(UserRegistrationInfo userRegistrationInfo);

    public String authorizationUser(UserAuthorizationInfo userAuthorizationInfo);

}
