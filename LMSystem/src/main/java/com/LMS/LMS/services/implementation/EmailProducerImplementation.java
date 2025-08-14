package com.LMS.LMS.services.implementation;

import com.LMS.LMS.services.inter.EmailProducerService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailProducerImplementation implements EmailProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic = "email_topic";

public EmailProducerImplementation(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendEmailRequest(String emailRequestJson) {
        kafkaTemplate.send(topic, emailRequestJson);
    }
}
