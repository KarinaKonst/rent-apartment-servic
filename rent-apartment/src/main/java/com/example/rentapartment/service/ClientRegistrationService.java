package com.example.rentapartment.service;


import com.example.rentapartment.dto.ClientDto;
import com.example.rentapartment.entity.ClientEntity;
import com.example.rentapartment.entity.RenterEntity;
import com.example.rentapartment.mapper.FullMapper;
import com.example.rentapartment.model.ResponseInfo;
import com.example.rentapartment.repository.ClientRepository;
import com.example.rentapartment.repository.RenterRepository;
import com.example.rentapartment.security_model.UserAuthorizationInfo;
import com.example.rentapartment.security_model.UserRegistrationInfo;
import com.example.rentapartment.security_model.ValideUserSession;
import com.example.rentapartment.validation.ValidationAtUserRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import static com.example.rentapartment.constant.ConstantProject.*;


@Service
public class ClientRegistrationService {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private RenterRepository renterRepository;
    @Autowired
    private FullMapper fullMapper;
    @Autowired
    private ValideUserSession valideUserSession;

    @Autowired
    ValidationAtUserRegistration validationAtUserRegistration;


    /**
     * Метод регистрации нового пользователя
     */
    public ResponseInfo registrationUser(UserRegistrationInfo userRegistrationInfo) {
        ResponseInfo responseInfo = new ResponseInfo();
        validationDuringRegistration(userRegistrationInfo);

        if (validationAtUserRegistration.getList().isEmpty()) {

            try {


                    if (clientRepository.getClientEntityByEmail(encode(userRegistrationInfo.getEmail())) == null) {
                        ClientEntity client = fullMapper.userRegistrationInfoToEntity(userRegistrationInfo);
                        encodeAndSetClientFields(client);
                        responseInfo.setMessage("Пользователь успешно зарегистрирован!Вы можете войти в систему.");
                        return responseInfo;
                    }
                    responseInfo.setErrorMessage(new ArrayList<>(Arrays.asList("Пользователь с таким именем уже существует!")));
                    return responseInfo;


            } catch (Exception e) {
                responseInfo.setErrorMessage(new ArrayList<>(Arrays.asList("Пользователь с таким именем уже существует!")));
                return responseInfo;
            }


        }

        responseInfo.setErrorMessage(new ArrayList<>(validationAtUserRegistration.getList()));
        validationAtUserRegistration.getList().clear();
        return responseInfo;
    }


    /**
     * Метод авторизации пользователя
     */

    public ResponseInfo authorizationUser(UserAuthorizationInfo userAuthorizationInfo) {
        ResponseInfo responseInfo = new ResponseInfo();

        String encodePassword = encode(userAuthorizationInfo.getPassword());
        String encodeEmail = encode(userAuthorizationInfo.getEmail());
        try {
            ClientEntity clientEntityByEmailAndPassword = clientRepository.getClientEntityByEmailAndPassword(encodeEmail, encodePassword);
            decodeAndSetClientEntityByEmailAndPassword(clientEntityByEmailAndPassword);

//            if (clientEntityByEmailAndPassword.getEmail() != null && clientEntityByEmailAndPassword.getPassword() != null) {
            setFieldsClientEntityByEmailAndPasswordToValideUserSession(clientEntityByEmailAndPassword);
            responseInfo.setMessage("Вы успешно вошли в систему!");
            return responseInfo;
//            }

//            responseInfo.setErrorMessage(new ArrayList<>(Arrays.asList("Пользователь не найден")));
//            return responseInfo;
        } catch (Exception e) {
            responseInfo.setErrorMessage(new ArrayList<>(Arrays.asList("Пользователь не найден! Зарегистрируйтесь.")));
        }
        return responseInfo;
    }


    /**
     * Метод кодирования
     */
    public String encode(String value) {
        Base64.Encoder encoder = Base64.getEncoder();
        String result = encoder.encodeToString(value.getBytes());
        return result;
    }

    /**
     * Метод декодирования
     */
    public String decode(String value) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(value);
        String result = new String(decode, StandardCharsets.UTF_8);
        return result;
    }

    /**
     * Метод валидации полей при регистрации
     */

    public void validationDuringRegistration(UserRegistrationInfo userRegistrationInfo) {
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
    public void decodeAndSetClientEntityByEmailAndPassword(ClientEntity clientEntityByEmailAndPassword) {
        String decodePassword = decode(clientEntityByEmailAndPassword.getPassword());
        String decodeEmail = decode(clientEntityByEmailAndPassword.getEmail());
        String decodeNumberPhone = decode(clientEntityByEmailAndPassword.getNumberPhone());
        clientEntityByEmailAndPassword.setPassword(decodePassword);
        clientEntityByEmailAndPassword.setNumberPhone(decodeNumberPhone);
        clientEntityByEmailAndPassword.setEmail(decodeEmail);
    }

    /**
     * Метод записи полей clientEntityByEmailAndPassword в valideUserSession
     */
    public void setFieldsClientEntityByEmailAndPasswordToValideUserSession(ClientEntity clientEntityByEmailAndPassword) {
        valideUserSession.setName(clientEntityByEmailAndPassword.getFirstName(), clientEntityByEmailAndPassword.getLastName());
        valideUserSession.setEmail(clientEntityByEmailAndPassword.getEmail());
        valideUserSession.setNumberPhone(clientEntityByEmailAndPassword.getNumberPhone());
        valideUserSession.setParentCity(clientEntityByEmailAndPassword.getParentCity());
        valideUserSession.setBirthday(clientEntityByEmailAndPassword.getBirthday());
    }

    /**
     * Метод кодирования и записи поля client
     */
    public void encodeAndSetClientFields(ClientEntity client) {
        client.setEmail(encode(client.getEmail()));
        client.setPassword(encode(client.getPassword()));
        client.setNumberPassport(encode((client.getNumberPassport())));
        client.setNumberPhone(encode(client.getNumberPhone()));
        clientRepository.save(client);
    }

    public void encodeAndRenterFields(RenterEntity renter) {
        renter.setEmail(encode(renter.getEmail()));
        renter.setPassword(encode(renter.getPassword()));
        renter.setNumberPhone(encode(renter.getNumberPhone()));
        renter.setNumberPassport(encode(renter.getNumberPassport()));
        renterRepository.save(renter);
    }

    public ClientDto getClientInfo(String email) {
        ClientEntity client = clientRepository.getClientEntityByEmail(email);
        String decodeEmail = decode(client.getEmail());

        ClientDto clientDto = fullMapper.clientEntityToClientDto(client);
        clientDto.setEmail(decodeEmail);
        return clientDto;
    }
}
