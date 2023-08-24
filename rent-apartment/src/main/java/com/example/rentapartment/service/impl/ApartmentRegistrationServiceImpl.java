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
import com.example.rentapartment.repository.RenterRepository;
import com.example.rentapartment.security_model.ValideUserSession;
import com.example.rentapartment.service.ApartmentRegistrationService;
import com.example.rentapartment.service.ClientRegistrationService;
import com.example.rentapartment.validation.ValidationAtApartmentRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.example.rentapartment.constant.ConstantProject.*;

@Service
public class ApartmentRegistrationServiceImpl implements ApartmentRegistrationService {

    @Autowired
    private FullMapper fullMapper;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private ValidationAtApartmentRegistration validationAtApartmentRegistration;
    @Autowired
    private ValideUserSession valideUserSession;
    @Autowired
    private RenterRepository renterRepository;
    @Autowired
    private ClientRegistrationService clientRegistrationService;
    @Autowired
    private ClientRepository clientRepository;


    @Override
    public ResponseInfo registrationApartment(ApartmentRegistration apartmentRegistration) {

        ResponseInfo responseInfo = new ResponseInfo();

            validationDuringRegistration(apartmentRegistration);

            if (validationAtApartmentRegistration.getList().isEmpty()) {

                apartmentRepository.save(fullMapper.apartmentRegistrationToApartmentEntity(apartmentRegistration));
                Long lastId = apartmentRepository.getLastId();
                AddressEntity addressEntity = fullMapper.apartmentRegistrationToAddressEntity(apartmentRegistration);
                ApartmentEntity apartmentEntity = apartmentRepository.findById(lastId).get();
                addressEntity.setApartmentEntity(apartmentEntity);
                addressRepository.save(addressEntity);
                responseInfo.setMessage("Квартира успешно зарегистрирована!");
                ClientEntity clientEntity = clientRepository.getClientEntityByEmail(clientRegistrationService
                        .encode(valideUserSession.getEmail()));
                addressEntity.getApartmentEntity().setOwner(clientEntity);
                clientEntity.setCommerce(true);
                apartmentEntity.setAvailability(true);
                apartmentRepository.save(apartmentEntity);
                clientRepository.save(clientEntity);
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
