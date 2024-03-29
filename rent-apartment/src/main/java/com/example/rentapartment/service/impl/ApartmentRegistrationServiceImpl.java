package com.example.rentapartment.service.impl;

import com.example.rentapartment.entity.AddressEntity;
import com.example.rentapartment.entity.ApartmentEntity;
import com.example.rentapartment.entity.ClientEntity;
import com.example.rentapartment.exception.NotFoundApartmentException;
import com.example.rentapartment.mapper.FullMapper;
import com.example.rentapartment.model.ApartmentRegistration;
import com.example.rentapartment.repository.AddressRepository;
import com.example.rentapartment.repository.ApartmentRepository;
import com.example.rentapartment.repository.ClientRepository;
import com.example.rentapartment.service.ApartmentRegistrationService;
import com.example.rentapartment.service.ValidateUserToken;
import com.example.rentapartment.validation.ValidationAtApartmentRegistration;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.example.rentapartment.constant.ConstantProject.*;

@Service
@RequiredArgsConstructor
public class ApartmentRegistrationServiceImpl implements ApartmentRegistrationService {
    private static final Logger logger = LoggerFactory.getLogger(ApartmentRegistrationServiceImpl.class);

    private final FullMapper fullMapper;
    private final AddressRepository addressRepository;
    private final ApartmentRepository apartmentRepository;
    private final ValidationAtApartmentRegistration validationAtApartmentRegistration;
    private final ValidateUserToken validateUserToken;
    private final ClientRepository clientRepository;


    @Override
    public String registrationApartment(ApartmentRegistration apartmentRegistration, String authToken) {
        logger.info("rent-apartment : registrationApartment -> started");


        validationDuringRegistration(apartmentRegistration);
        if (validationAtApartmentRegistration.getList().isEmpty()) {
            AddressEntity addressEntity = addressRepository.getAddressEntitiesByCityAndStreetAndNumberHouseAndNumberApartment(apartmentRegistration.getCity(),
                    apartmentRegistration.getStreet(),
                    apartmentRegistration.getNumberHouse(),
                    apartmentRegistration.getNumberApartment());
            if (addressEntity != null) {
                throw new NotFoundApartmentException(APART_EXISTS);
            }
            ApartmentEntity apartment = fullMapper.apartmentRegistrationToApartmentEntity(apartmentRegistration);
            apartmentRepository.save(apartment);
            AddressEntity address = fullMapper.apartmentRegistrationToAddressEntity(apartmentRegistration);
            address.setApartmentEntity(apartment);
            addressRepository.save(address);

            ClientEntity clientEntity = clientRepository.getClientEntityBySessionToken(authToken);

            address.getApartmentEntity().setOwner(clientEntity);
            clientEntity.setCommerce(true);
            apartment.setAvailability(true);
            apartmentRepository.save(apartment);
            clientRepository.save(clientEntity);
            return GOOD_REG_APART;

        }
        ArrayList<String> list = new ArrayList<>(validationAtApartmentRegistration.getList());
        validationAtApartmentRegistration.getList().clear();
        return list.toString();
    }


    public void validationDuringRegistration(ApartmentRegistration apartmentRegistration) {

        validationAtApartmentRegistration.checkingForZero(STREET, apartmentRegistration.getStreet());
        validationAtApartmentRegistration.checkingForZero(CITY, apartmentRegistration.getCity());
        validationAtApartmentRegistration.checkingForZero(NUMBER_HOUSE, apartmentRegistration.getNumberHouse());
        validationAtApartmentRegistration.checkingForZero(NUMBER_APARTMENT, apartmentRegistration.getNumberApartment());
        validationAtApartmentRegistration.checkingForZero(NUMBER_OF_ROOMS, apartmentRegistration.getNumberOfRooms());
        validationAtApartmentRegistration.checkingForZero(PRICE, apartmentRegistration.getPrice());

        validationAtApartmentRegistration.checkStringFields(STREET, apartmentRegistration.getStreet());
        validationAtApartmentRegistration.checkStringFields(CITY, apartmentRegistration.getCity());

        validationAtApartmentRegistration.checkingForIntegers(NUMBER_HOUSE, apartmentRegistration.getNumberHouse());
        validationAtApartmentRegistration.checkingForIntegers(NUMBER_APARTMENT, apartmentRegistration.getNumberApartment());
        validationAtApartmentRegistration.checkingForIntegers(NUMBER_OF_ROOMS, apartmentRegistration.getNumberOfRooms());
        validationAtApartmentRegistration.checkingForIntegers(PRICE, apartmentRegistration.getNumberApartment());
    }
}
