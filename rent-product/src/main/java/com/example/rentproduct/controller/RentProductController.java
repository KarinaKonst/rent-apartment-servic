package com.example.rentproduct.controller;

import com.example.rentproduct.model.IntegrationModelToMailSendler;
import com.example.rentproduct.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.rentproduct.constant.Constant.SEND_PRODUCT;

@RestController
public class RentProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(SEND_PRODUCT)
    public void productSend(@RequestParam Long id) {
        productService.throwInfoToProductService(id);
    }
}
