package com.example.rentapartment.exception;

import lombok.Getter;

@Getter
public class NotFoundUser extends RuntimeException{
    private final String EXCEPTION_NOT_FOUND_USER="Авторизируйтесь!";
}
