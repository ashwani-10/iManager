package com.example.iManager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    public void produceMessage(Object message,String key, String topic) throws JsonProcessingException {
        System.out.println("inside sendMessage");
        kafkaTemplate.send(topic,key,message);
        System.out.println("Message sent to kafka topic: "+ topic);
    }
}
