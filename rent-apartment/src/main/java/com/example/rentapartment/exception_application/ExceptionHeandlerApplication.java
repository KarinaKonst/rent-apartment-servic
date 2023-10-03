package com.example.rentapartment.exception_application;

import com.example.rentapartment.exception.NotFoundBodyResponseException;
import com.example.rentapartment.exception.NotFoundInformation;
import com.example.rentapartment.exception.NotFoundUser;
import com.example.rentapartment.response_object.ResponseObjectList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionHeandlerApplication {

    @ExceptionHandler(NotFoundBodyResponseException.class)
    public ResponseEntity<?> responseNotFoundBody(NotFoundBodyResponseException e){
        ResponseObjectList responseObjectList=new ResponseObjectList(e.getEXCEPTION_MESSAGE_NOT_FOUND_BODY(),null);
        return  ResponseEntity
                .badRequest()
                .body(responseObjectList);
    }
    @ExceptionHandler(NotFoundInformation.class)
    public ResponseEntity<?> responseNotFoundInformation(NotFoundInformation e){
        ResponseObjectList responseObjectList=new ResponseObjectList(e.getEXCEPTION_MESSAGE_NOT_FOUND_INFORMATION(),null);
        return  ResponseEntity
                .badRequest()
                .body(responseObjectList);
    }
    @ExceptionHandler(NotFoundUser.class)
    public ResponseEntity<?>responseNotFoundUser(NotFoundUser e){
        ResponseObjectList responseObjectList=new ResponseObjectList(e.getEXCEPTION_NOT_FOUND_USER(),null);
        return ResponseEntity.badRequest().body(responseObjectList);
    }

}
