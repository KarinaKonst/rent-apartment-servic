package com.example.rentapartment.service.impl;

import com.example.rentapartment.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerServiceImpl implements ProducerService {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Override
    public String getProducerInfo(String s) {
        try {
            kafkaTemplate.send("myTopic", s);
            return "Сообщение отправлено в топик";
        } catch (Exception e) {
            return "Сообщение не отправлено в топик";
        }

    }
}

