package com.example.rentapartment.response_object;


import com.example.rentapartment.dto.AddressDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter
@ToString
@AllArgsConstructor
public class ResponseObjectList {
     private String message;
     private List<AddressDto> responseAddressList;


}
