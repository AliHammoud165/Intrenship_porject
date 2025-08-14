package com.EmailSendingMicroService.EmailSendingMicroService.services;

import com.EmailSendingMicroService.EmailSendingMicroService.dtos.EmailRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailConsumerService {

    private final EmailSendingService emailSendingService;
    private final ObjectMapper objectMapper;

    public EmailConsumerService(EmailSendingService emailSendingService, ObjectMapper objectMapper) {
        this.emailSendingService = emailSendingService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "email_topic", groupId = "email-service")
    public void consumeEmail(String emailJson) throws Exception {
        EmailRequest emailRequest = objectMapper.readValue(emailJson, EmailRequest.class);
        emailSendingService.sendSimpleEmail(emailRequest); // or use MimeMessage version
    }
}

