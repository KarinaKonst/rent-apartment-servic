package com.example.rentapartment.exception_application;

import com.example.rentapartment.response_object.ResponseObjectList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.rentapartment.exception_application.NotFoundBodyResponseException.EXCEPTION_MESSAGE_NOT_FOUND_BODY;
import static com.example.rentapartment.exception_application.NotFoundInformation.EXCEPTION_MESSAGE_NOT_FOUND_INFORMATION;


@RestControllerAdvice
public class ExceptionHeandlerApplication {

    @ExceptionHandler(NotFoundBodyResponseException.class)
    public ResponseEntity<?> responseNotFoundBody(NotFoundBodyResponseException e){
        ResponseObjectList responseObjectList=new ResponseObjectList(EXCEPTION_MESSAGE_NOT_FOUND_BODY,null);
        return  ResponseEntity
                .badRequest()
                .body(responseObjectList);
    }
    @ExceptionHandler(NotFoundInformation.class)
    public ResponseEntity<?> responseNotFoundInformation(NotFoundInformation e){
        ResponseObjectList responseObjectList=new ResponseObjectList(EXCEPTION_MESSAGE_NOT_FOUND_INFORMATION,null);
        return  ResponseEntity
                .badRequest()
                .body(responseObjectList);
    }

}
