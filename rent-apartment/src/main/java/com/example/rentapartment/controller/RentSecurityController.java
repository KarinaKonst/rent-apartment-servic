package com.example.rentapartment.controller;

import com.example.rentapartment.security_model.UserAuthorizationInfo;
import com.example.rentapartment.security_model.UserRegistrationInfo;
import com.example.rentapartment.service.ClientRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.example.rentapartment.constant.ConstantProject.USER_AUTHORIZATION;
import static com.example.rentapartment.constant.ConstantProject.USER_REGISTRATION;

@RestController
public class RentSecurityController {

    @Autowired
    private ClientRegistrationService clientRegistrationService;


    /**
     * Метод регистрации нового пользователя
     */
    @PostMapping(USER_REGISTRATION)
    public String registrationUser(@RequestBody UserRegistrationInfo userRegistrationInfo) {
        return clientRegistrationService.registrationUser(userRegistrationInfo);
    }

    /**
     * Метод авторизации нового пользователя
     */
    @PostMapping(USER_AUTHORIZATION)
    public String authorizationUser(@RequestBody UserAuthorizationInfo userAuthorizationInfo) {
        return clientRegistrationService.authorizationUser(userAuthorizationInfo);
    }

}
