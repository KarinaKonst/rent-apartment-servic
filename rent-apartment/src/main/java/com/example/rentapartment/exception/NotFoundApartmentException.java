package com.example.rentapartment.exception;

public class NotFoundApartmentException extends RuntimeException{

    public NotFoundApartmentException(String message){
        super(message);
    }
}
