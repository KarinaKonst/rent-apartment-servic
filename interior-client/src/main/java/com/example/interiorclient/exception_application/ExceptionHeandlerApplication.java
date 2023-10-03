package com.example.interiorclient.exception_application;

import com.example.interiorclient.exception.NotFoundBookingHistoryException;
import com.example.interiorclient.response_object.ResponseObjectList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class ExceptionHeandlerApplication {
    @ExceptionHandler(NotFoundBookingHistoryException.class)
    public ResponseEntity<?>responseNotFoundHistory(NotFoundBookingHistoryException e){
        ResponseObjectList responseObjectList=new ResponseObjectList(e.getEXCEPTION_DESCRIPTION(),null);
        return  ResponseEntity
                .badRequest()
                .body(responseObjectList);
    }

}
