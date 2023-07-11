package com.example.rentproduct.controller;


import com.example.rentproduct.dto.AddressDto;
import com.example.rentproduct.dto.ClientDto;
import com.example.rentproduct.service.impl.RentProductSeviceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RentProductController {

    @Autowired
    private RentProductSeviceImpl rentProductSevice;

    @PostMapping("/save")
    public String registrationApartment(@RequestBody ClientDto clientInfo, @RequestBody AddressDto addressDto) {
        String s = rentProductSevice.throwSendlerOnRentApartmentService(clientInfo, addressDto);
        return s;
    }
}
