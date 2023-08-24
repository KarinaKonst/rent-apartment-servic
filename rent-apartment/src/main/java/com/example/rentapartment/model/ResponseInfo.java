package com.example.rentapartment.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ResponseInfo extends ErrorResponseInfo {

    private String message;

    @Override
    public String toString() {

        if(getErrorMessage()!=null){
            return
                    "ОШИБКА! " + getErrorMessage();
        }
        return
                 message ;
    }



}
