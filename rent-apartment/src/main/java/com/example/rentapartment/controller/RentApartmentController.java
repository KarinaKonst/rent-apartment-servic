package com.example.rentapartment.controller;

import com.example.rentapartment.dto.AddressDto;
import com.example.rentapartment.model.ApartmentRegistration;
import com.example.rentapartment.model.FeedbackModel;
import com.example.rentapartment.response_object.ResponseObjectList;
import com.example.rentapartment.service.ApartmentRegistrationService;
import com.example.rentapartment.service.RentApartmentService;
import com.example.rentapartment.service.ValidateUserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static com.example.rentapartment.constant.ConstantProject.*;

@RestController
@RequestMapping(API)
public class RentApartmentController {

    @Autowired
    private RentApartmentService rentApartmentService;
    @Autowired
    private ValidateUserToken validateUserToken;
    @Autowired
    private ApartmentRegistrationService apartmentRegistrationService;


    /**
     * Метод регистрации новой квартиры
     */
    @PostMapping(REGISTRATION_APARTMENT)
    public String registrationNewApartment(@RequestHeader String auth_token,
                                           @RequestBody ApartmentRegistration apartmentRegistration) {
        validateUserToken.checkValidateSession(auth_token);
        return apartmentRegistrationService.registrationApartment(apartmentRegistration, auth_token);

    }

    /**
     * Метод возвращает список адресов квартир по названию улицы
     */
    @GetMapping(GET_INFO_BY_STREET)
    public ResponseObjectList getInfoByStreet(@RequestParam String street) {
        return rentApartmentService.getFullInformationByStreet(street);

    }

    /**
     * Метод возвращает список адресов квартир по широте и долготе
     */
    @GetMapping(GET_INFO_BY_CITY)
    public ResponseObjectList getInfoByCity(@RequestParam String latitude, @RequestParam String longitude) {

        return rentApartmentService.getInfoByCity(latitude, longitude);
    }

    /**
     * Метод возвращает адрес квартиры с возможностью дальнейшего бронирования
     */
    @GetMapping(CHOICE_APARTMENT)
    public AddressDto choiceApartmentById(@RequestHeader(required = false) String auth_token,
                                          @RequestParam Long id,
                                          @RequestParam(required = false) LocalDate start,
                                          @RequestParam(required = false) LocalDate end) {

        if (start == null && end == null) {
            AddressDto addressDto = rentApartmentService.getApartmentById(id);

            return addressDto;
        }
        validateUserToken.checkValidateSession(auth_token);
        return rentApartmentService.getApartmentByIdAndTotalAmount(id, auth_token, start, end);
    }

    /**
     * Метод возвращает адрес квартир по цене и количеству комнат
     */
    @GetMapping(GET_APARTMENT_BY_PRICE_AND_NUMBER_OF_ROOMS)
    public ResponseObjectList getApartmentEntitiesByPriceAndNumberOfRooms(@RequestParam String price,
                                                                          @RequestParam String numberOfRooms) {

        return rentApartmentService.getApartmentEntitiesByPriceAndNumberOfRooms(price, numberOfRooms);
    }
    /**
     * Метод позволяет оставить отзыв о квартире
     */
    @PostMapping(FEEDBACK)
    public String sendFeedback(@RequestHeader String auth_token,
                               @RequestBody FeedbackModel feedbackModel){
        validateUserToken.checkValidateSession(auth_token);

         return rentApartmentService.sendFeedback(feedbackModel);


    }


}
