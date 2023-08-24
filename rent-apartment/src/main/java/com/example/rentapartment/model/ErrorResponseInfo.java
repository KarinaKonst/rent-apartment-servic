package com.example.rentapartment.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ErrorResponseInfo {

    private List<String> errorMessage;
}
