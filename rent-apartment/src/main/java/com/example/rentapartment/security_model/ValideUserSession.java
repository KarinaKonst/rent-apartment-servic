package com.example.rentapartment.security_model;

import com.example.rentapartment.exception.NotFoundInformation;
import com.example.rentapartment.exception.NotFoundUser;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static java.util.Objects.isNull;

@Component
@Data
public class ValideUserSession {

    private String name;
    private String numberPhone;
    private String parentCity;
    private String email;
    private LocalDate birthday;

    public void setName(String firstName,String lastName) {
        this.name = firstName+ " "+lastName;
    }


    /** Метод проверяет является ли поле "Email" пустым*/
    public void checkValideSession() {
        if (isNull(email)){
            throw new NotFoundUser();
        }
    }
}
