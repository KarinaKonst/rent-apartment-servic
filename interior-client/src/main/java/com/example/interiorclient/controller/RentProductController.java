package com.example.interiorclient.controller;

import com.example.interiorclient.service.ConsumerService;
import com.example.interiorclient.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.interiorclient.constant.Constant.SEND_PRODUCT;

@RestController
public class RentProductController {

    @Autowired
    private ProductService productService;


    @PostMapping(SEND_PRODUCT)
    public void productSend(@RequestParam Long id) {
        productService.throwInfoToProductService(id);
    }
}
