package com.example.interiorclient.service.impl;

import com.example.interiorclient.consumer.ConsumerListener;
import com.example.interiorclient.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private ConsumerListener consumerListener;

    @Override
    public List<String> getMessage() {
        return consumerListener.getMessages().stream().sorted().collect(Collectors.toList());
    }
}
