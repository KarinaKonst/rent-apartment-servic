package com.example.rentapartment.service.impl;

import com.example.rentapartment.service.Base64Manager;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class Base64ManagerImpl implements Base64Manager {
    @Override
    public String encode(String value) {
        Base64.Encoder encoder = Base64.getEncoder();
        String result = encoder.encodeToString(value.getBytes());
        return result;
    }

    @Override
    public String decode(String value) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(value);
        String result = new String(decode, StandardCharsets.UTF_8);
        return result;
    }
}
