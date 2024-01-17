package com.example.interiorclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@EnableScheduling
@Service
public class SchedulerService {
    @Autowired
    private ConsumerService consumerService;
    @Autowired
    private ProductService productService;

    @Scheduled(fixedDelay = 60000)
    public void checkMessage(){
        List<String> message = consumerService.getMessage();
        if(!message.isEmpty()){
            for (String s : message) {
                long idKafka = Long.valueOf(s).longValue();
                productService.sendMessage(idKafka);
            }
        }

    }

}
