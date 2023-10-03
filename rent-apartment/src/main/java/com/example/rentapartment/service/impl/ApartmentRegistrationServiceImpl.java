package com.example.rentapartment.service.impl;

import com.example.rentapartment.entity.AddressEntity;
import com.example.rentapartment.entity.ApartmentEntity;
import com.example.rentapartment.entity.ClientEntity;
import com.example.rentapartment.mapper.FullMapper;
import com.example.rentapartment.model.ApartmentRegistration;
import com.example.rentapartment.model.ResponseInfo;
import com.example.rentapartment.repository.AddressRepository;
import com.example.rentapartment.repository.ApartmentRepository;
import com.example.rentapartment.repository.ClientRepository;
import com.example.rentapartment.security_model.ValideUserSession;
import com.example.rentapartment.service.ApartmentRegistrationService;
import com.example.rentapartment.service.ClientRegistrationService;
import com.example.rentapartment.validation.ValidationAtApartmentRegistration;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.rentapartment.constant.ConstantProject.*;

@Service
@RequiredArgsConstructor
public class ApartmentRegistrationServiceImpl implements ApartmentRegistrationService {
    private static final Logger logger = LoggerFactory.getLogger(ApartmentRegistrationServiceImpl.class);

    private final FullMapper fullMapper;

    private final AddressRepository addressRepository;

    private final ApartmentRepository apartmentRepository;

    private final ValidationAtApartmentRegistration validationAtApartmentRegistration;

    private final ValideUserSession valideUserSession;

    private final ClientRegistrationService clientRegistrationService;

    private final ClientRepository clientRepository;
    private Base64ManagerImpl base64Manager;


    @Override
    public ResponseInfo registrationApartment(ApartmentRegistration apartmentRegistration) {
        logger.debug("rent-apartment : registrationApartment -> started");
        logger.info("rent-apartment : registrationApartment -> started");
        logger.error("rent-apartment : registrationApartment -> started");

        ResponseInfo responseInfo = new ResponseInfo();

        validationDuringRegistration(apartmentRegistration);

        if (validationAtApartmentRegistration.getList().isEmpty()) {
            if (addressRepository.getAddressEntitiesByCityAndStreetAndNumberHouseAndNumberApartment(apartmentRegistration.getCity(),
                    apartmentRegistration.getStreet(),
                    apartmentRegistration.getNumberHouse(),
                    apartmentRegistration.getNumberApartment()) == null) {

                apartmentRepository.save(fullMapper.apartmentRegistrationToApartmentEntity(apartmentRegistration));
                Long lastId = apartmentRepository.getLastId();
                AddressEntity addressEntity = fullMapper.apartmentRegistrationToAddressEntity(apartmentRegistration);
                ApartmentEntity apartmentEntity = apartmentRepository.findById(lastId).get();
                addressEntity.setApartmentEntity(apartmentEntity);
                addressRepository.save(addressEntity);
                responseInfo.setMessage("Квартира успешно зарегистрирована!");

                ClientEntity clientEntity = clientRepository.getClientEntityByEmail(base64Manager.encode(valideUserSession.getEmail()));
                addressEntity.getApartmentEntity().setOwner(clientEntity);
                clientEntity.setCommerce(true);
                apartmentEntity.setAvailability(true);
                apartmentRepository.save(apartmentEntity);
                clientRepository.save(clientEntity);
                return responseInfo;
            }
            responseInfo.setErrorMessage(new ArrayList<>(Arrays.asList("Квартира с таким адресом уже существует!")));
            return responseInfo;

        }
        responseInfo.setErrorMessage(new ArrayList<>(validationAtApartmentRegistration.getList()));
        validationAtApartmentRegistration.getList().clear();
        return responseInfo;
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
