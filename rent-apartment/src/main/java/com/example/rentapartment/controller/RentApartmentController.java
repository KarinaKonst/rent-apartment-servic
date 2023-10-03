package com.example.rentapartment.controller;

import com.example.rentapartment.dto.AddressDto;
import com.example.rentapartment.model.ApartmentRegistration;
import com.example.rentapartment.model.ResponseInfo;
import com.example.rentapartment.response_object.ResponseObjectList;
import com.example.rentapartment.security_model.UserAuthorizationInfo;
import com.example.rentapartment.security_model.UserRegistrationInfo;
import com.example.rentapartment.security_model.ValideUserSession;
import com.example.rentapartment.service.ApartmentRegistrationService;
import com.example.rentapartment.service.ClientRegistrationService;
import com.example.rentapartment.service.IntegrationManager;
import com.example.rentapartment.service.RentApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.example.rentapartment.constant.ConstantProject.*;

@RestController
@RequestMapping(API)
public class RentApartmentController {

    @Autowired
    private RentApartmentService rentApartmentService;
    @Autowired
    private ClientRegistrationService clientRegistrationService;
    @Autowired
    private ValideUserSession valideUserSession;

    @Autowired
    private ApartmentRegistrationService apartmentRegistrationService;
    @Autowired
    private IntegrationManager integrationManager;


    @PostMapping(REGISTRATION_APARTMENT)
    public String registrationNewApartment(@RequestBody ApartmentRegistration apartmentRegistration) {
        valideUserSession.checkValideSession();
        ResponseInfo responseInfo = apartmentRegistrationService.registrationApartment(apartmentRegistration);
        return responseInfo.toString();
    }

    /**
     * Метод возвращает список адресов квартир по названию улицы
     */
    @GetMapping(GET_INFO_BY_STREET)
    public ResponseObjectList getInfoByStreet(@PathVariable String street) {
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
    public AddressDto choiceApartmentById(@RequestParam Long id, @RequestParam(required = false) LocalDate start,
                                          @RequestParam(required = false) LocalDate end) {
        valideUserSession.checkValideSession();
        if (start == null && end == null) {
            AddressDto addressDto = rentApartmentService.getApartmentById(id);

            return addressDto;
        }
        return rentApartmentService.getApartmentByIdAndTotalAmount(id, start, end);
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
     * Метод регистрации нового пользователя
     */
    @PostMapping(USER_REGISTRATION)
    public String registrationUser(@RequestBody UserRegistrationInfo userRegistrationInfo) {
        ResponseInfo responseInfo = clientRegistrationService.registrationUser(userRegistrationInfo);
        return responseInfo.toString();

    }

    /**
     * Метод авторизации нового пользователя
     */
    @PostMapping(USER_AUTHORIZATION)
    public String authorizationUser(@RequestBody UserAuthorizationInfo userAuthorizationInfo) {
        ResponseInfo responseInfo = clientRegistrationService.authorizationUser(userAuthorizationInfo);
        return responseInfo.toString();

    }

    @PostMapping("/conclusion-of-transaction")
    public String conclusionOfTransaction(@RequestParam int days, @RequestParam Long id) {
        valideUserSession.checkValideSession();
        AddressDto apartmentById = rentApartmentService.getApartmentById(id);
        rentApartmentService.getTotalAmount(apartmentById, days);

        return "Успешно!";
    }


}
