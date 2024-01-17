package com.example.rentapartment.service;

import com.example.rentapartment.dto.AddressDto;
import com.example.rentapartment.entity.RaitingEntity;
import com.example.rentapartment.model.FeedbackModel;
import com.example.rentapartment.response_object.ResponseObjectList;

import java.time.LocalDate;
import java.util.List;

public interface RentApartmentService {

    ResponseObjectList getFullInformationByStreet(String street);

    ResponseObjectList getInfoByCity(String latitude, String longitude);

    ResponseObjectList getApartmentEntitiesByPriceAndNumberOfRooms(String price, String numberOfRooms);

    AddressDto getApartmentById(Long id);

    AddressDto getApartmentByIdAndTotalAmount(Long id, String authToken, LocalDate start, LocalDate end);

    String sendFeedback(FeedbackModel feedbackModel);

    Integer getAverageValueRaiting(List<RaitingEntity> result);



}
