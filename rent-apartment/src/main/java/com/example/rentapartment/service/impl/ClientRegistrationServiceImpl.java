package com.example.rentapartment.service.impl;

import com.example.rentapartment.entity.ClientEntity;
import com.example.rentapartment.exception.NotFoundUserException;
import com.example.rentapartment.mapper.FullMapper;
import com.example.rentapartment.repository.ClientRepository;
import com.example.rentapartment.security_model.UserAuthorizationInfo;
import com.example.rentapartment.security_model.UserRegistrationInfo;
import com.example.rentapartment.service.Base64Manager;
import com.example.rentapartment.service.ClientRegistrationService;
import com.example.rentapartment.validation.ValidationAtUserRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static com.example.rentapartment.constant.ConstantProject.*;
import static java.util.Objects.isNull;


@Service
@RequiredArgsConstructor
public class ClientRegistrationServiceImpl implements ClientRegistrationService {

    private final ClientRepository clientRepository;
    private final FullMapper fullMapper;
    private final ValidationAtUserRegistration validationAtUserRegistration;
    private final Base64Manager base64Manager;


    /**
     * Метод регистрации нового пользователя
     */
    public String registrationUser(UserRegistrationInfo userRegistrationInfo) {
        validationDuringRegistration(userRegistrationInfo);
        if (validationAtUserRegistration.getList().isEmpty()) {
            ClientEntity client = clientRepository.getClientEntityByEmail(userRegistrationInfo.getEmail());
            if (!isNull(client)) {
                throw new NotFoundUserException(USER_EXISTS);
            }
            ClientEntity clientEntity = fullMapper.userRegistrationInfoToEntity(userRegistrationInfo);
            encodeAndSetClientFields(clientEntity);
            return GOOD_REG_USER;
        }
        ArrayList<String> arrayList = new ArrayList<>(validationAtUserRegistration.getList());
        validationAtUserRegistration.getList().clear();
        return arrayList.toString();
    }



    /**
     * Метод авторизации пользователя
     */

    public String authorizationUser(UserAuthorizationInfo userAuthorizationInfo) {

        ClientEntity client = clientRepository.getClientEntityByEmail(userAuthorizationInfo.getEmail());
        if (isNull(client)) {
            throw new NotFoundUserException(EXCEPTION_NOT_FOUND_USER);
        }
        if (!base64Manager.decode(client.getPassword()).equals(userAuthorizationInfo.getPassword())) {
            throw new NotFoundUserException(EXCEPTION_BAD_PASSWORD);
        }
        String token = generateAuthToken();
        client.setSessionToken(token);
        clientRepository.save(client);
        return token;

    }


    private String generateAuthToken() {
        String uniqueToken = UUID.randomUUID().toString();
        return uniqueToken + "|" + LocalDateTime.now().plusDays(1);

    }


    /**
     * Метод валидации полей при регистрации
     */

    private void validationDuringRegistration(UserRegistrationInfo userRegistrationInfo) {
        validationAtUserRegistration.checkingForZero(FIRST_NAME, userRegistrationInfo.getFirstName());
        validationAtUserRegistration.checkNameClient(FIRST_NAME, userRegistrationInfo.getFirstName());

        validationAtUserRegistration.checkingForZero(LAST_NAME, userRegistrationInfo.getLastName());
        validationAtUserRegistration.checkNameClient(LAST_NAME, userRegistrationInfo.getLastName());

        validationAtUserRegistration.checkingForZero(NUMBER_PASSPORT, userRegistrationInfo.getNumberPassport());
        validationAtUserRegistration.checkNumberPassport(userRegistrationInfo.getNumberPassport());

        validationAtUserRegistration.checkingForZero(NUMBER_PHONE, userRegistrationInfo.getNumberPhone());
        validationAtUserRegistration.checkNumberPhone(userRegistrationInfo.getNumberPhone());

        validationAtUserRegistration.checkingForZero(PARENT_CITY, userRegistrationInfo.getParentCity());
        validationAtUserRegistration.checkNameClient(PARENT_CITY, userRegistrationInfo.getParentCity());

        validationAtUserRegistration.checkingForZero(EMAIL, userRegistrationInfo.getEmail());
        validationAtUserRegistration.checkEmail(userRegistrationInfo.getEmail());

        validationAtUserRegistration.checkingForZero(PASSWORD, userRegistrationInfo.getPassword());
        validationAtUserRegistration.checkPassword(userRegistrationInfo.getPassword());
    }

    /**
     * Метод декодирования и записи поля ClientEntityByEmailAndPassword
     */
    private void decodeAndSetClientEntityByEmailAndPassword(ClientEntity clientEntityByEmailAndPassword) {
        String decodePassword = base64Manager.decode(clientEntityByEmailAndPassword.getPassword());
        String decodeEmail = base64Manager.decode(clientEntityByEmailAndPassword.getEmail());
        String decodeNumberPhone = base64Manager.decode(clientEntityByEmailAndPassword.getNumberPhone());
        clientEntityByEmailAndPassword.setPassword(decodePassword);
        clientEntityByEmailAndPassword.setNumberPhone(decodeNumberPhone);
        clientEntityByEmailAndPassword.setEmail(decodeEmail);
    }

    /**
     * Метод кодирования и записи поля client
     */
    private void encodeAndSetClientFields(ClientEntity client) {
        client.setEmail(client.getEmail());
        client.setPassword(base64Manager.encode(client.getPassword()));
        client.setNumberPassport(client.getNumberPassport());
        client.setNumberPhone(client.getNumberPhone());
        clientRepository.save(client);
    }

}
