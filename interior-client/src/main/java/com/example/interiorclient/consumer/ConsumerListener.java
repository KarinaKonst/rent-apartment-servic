package com.example.interiorclient.consumer;

import lombok.Getter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class ConsumerListener {

    private final List<String> messages = new ArrayList<>();

    @KafkaListener(topics="myTopic",groupId = "kafka-sandbox")
    public void listen(String message) {
        synchronized (messages) {
            messages.add(message);
        }
    }
}
