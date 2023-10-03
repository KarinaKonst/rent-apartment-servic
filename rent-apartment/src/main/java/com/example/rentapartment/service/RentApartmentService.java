package com.example.rentapartment.service;

import com.example.rentapartment.dto.AddressDto;
import com.example.rentapartment.response_object.ResponseObjectList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RentApartmentService {

//    String saveToBase(RegistrationFlatFormModel registrationFlatFormModel);

  ResponseObjectList getFullInformationByStreet(String street);

//    List<AddressDto> getFullInformationByCity(String city);

    ResponseObjectList getInfoByCity(String latitude , String  longitude) ;

    ResponseObjectList getApartmentEntitiesByPriceAndNumberOfRooms(String price, String numberOfRooms);
    AddressDto getApartmentById(Long id);
    Integer getTotalAmount(AddressDto addressDto, int days);
    AddressDto getApartmentByIdAndTotalAmount(Long id, LocalDate start, LocalDate end);


}
