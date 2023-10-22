package com.example.rentapartment.service;

import com.example.rentapartment.model.ApartmentRegistration;
import com.example.rentapartment.model.ResponseInfo;

public interface ApartmentRegistrationService {
    public String registrationApartment(ApartmentRegistration apartmentRegistration,String authToken);
}
