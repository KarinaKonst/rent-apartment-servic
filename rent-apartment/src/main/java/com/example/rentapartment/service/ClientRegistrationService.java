package com.example.rentapartment.service;


import com.example.rentapartment.dto.ClientDto;
import com.example.rentapartment.entity.ClientEntity;
import com.example.rentapartment.mapper.FullMapper;
import com.example.rentapartment.model.ResponseInfo;
import com.example.rentapartment.repository.ClientRepository;
import com.example.rentapartment.security_model.UserAuthorizationInfo;
import com.example.rentapartment.security_model.UserRegistrationInfo;
import com.example.rentapartment.security_model.ValideUserSession;
import com.example.rentapartment.validation.ValidationAtUserRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import static com.example.rentapartment.constant.ConstantProject.*;


@Service
@RequiredArgsConstructor
public class ClientRegistrationService {


    private final ClientRepository clientRepository;

    private final FullMapper fullMapper;
    private final ValideUserSession valideUserSession;
  private final ValidationAtUserRegistration validationAtUserRegistration;
  private final Base64Manager base64Manager;


    /**
     * Метод регистрации нового пользователя
     */
    public ResponseInfo registrationUser(UserRegistrationInfo userRegistrationInfo) {
        ResponseInfo responseInfo = new ResponseInfo();
        validationDuringRegistration(userRegistrationInfo);

        if (validationAtUserRegistration.getList().isEmpty()) {

            try {
                    if (clientRepository.getClientEntityByEmail(base64Manager.encode(userRegistrationInfo.getEmail())) == null) {
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

        String encodePassword = base64Manager.encode(userAuthorizationInfo.getPassword());
        String encodeEmail = base64Manager.encode(userAuthorizationInfo.getEmail());
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
        String decodePassword = base64Manager.decode(clientEntityByEmailAndPassword.getPassword());
        String decodeEmail = base64Manager.decode(clientEntityByEmailAndPassword.getEmail());
        String decodeNumberPhone = base64Manager.decode(clientEntityByEmailAndPassword.getNumberPhone());
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
        client.setEmail(base64Manager.encode(client.getEmail()));
        client.setPassword(base64Manager.encode(client.getPassword()));
        client.setNumberPassport(base64Manager.encode((client.getNumberPassport())));
        client.setNumberPhone(base64Manager.encode(client.getNumberPhone()));
        clientRepository.save(client);
    }


    public ClientDto getClientInfo(String email) {
        ClientEntity client = clientRepository.getClientEntityByEmail(email);
        String decodeEmail = base64Manager.decode(client.getEmail());

        ClientDto clientDto = fullMapper.clientEntityToClientDto(client);
        clientDto.setEmail(decodeEmail);
        return clientDto;
    }
}
