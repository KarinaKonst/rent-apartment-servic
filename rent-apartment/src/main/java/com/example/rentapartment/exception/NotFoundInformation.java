package com.example.rentapartment.exception;

import lombok.Getter;

@Getter
public class NotFoundInformation extends  RuntimeException{
    private final  String EXCEPTION_MESSAGE_NOT_FOUND_INFORMATION="Информация не найдена.";
}
