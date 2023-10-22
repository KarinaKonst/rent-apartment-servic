package com.example.rentapartment.service.impl;

import com.example.rentapartment.entity.ClientEntity;
import com.example.rentapartment.exception.NotFoundUserException;
import com.example.rentapartment.repository.ClientRepository;
import com.example.rentapartment.service.ValidateUserToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.rentapartment.constant.ConstantProject.EXCEPTION_AUTH;
import static java.util.Objects.isNull;

@Service
public class ValidateUserTokenImpl implements ValidateUserToken {

    @Autowired
    private ClientRepository clientRepository;

    public void checkValidateSession(String authToken) {
        ClientEntity user = clientRepository.getClientEntityBySessionToken(authToken);
        if (isNull(user)) {
            throw new NotFoundUserException(EXCEPTION_AUTH);
        }
    }

    @Override
    public String getEmailSession(String authToken) {
        ClientEntity user = clientRepository.getClientEntityBySessionToken(authToken);
        return user.getEmail();
    }
}
