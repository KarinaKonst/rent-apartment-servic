package com.example.rentapartment.exception;

import lombok.Getter;

@Getter
public class NotFoundBodyResponseException extends  RuntimeException{
    private final String EXCEPTION_MESSAGE_NOT_FOUND_BODY="Информация временно недоступна. Попробуйте позже.";
}
