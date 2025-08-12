package com.EmailSendingMicroService.EmailSendingMicroService.services;

import com.EmailSendingMicroService.EmailSendingMicroService.dtos.EmailRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendingService {

    private static final Logger logger = LoggerFactory.getLogger(EmailSendingService.class);

    private final JavaMailSender javaMailSender;

    public void sendSimpleEmail(EmailRequest emailRequest) {
        logger.info("Preparing to send email to {}", emailRequest.getTo());

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(emailRequest.getTo());
        simpleMailMessage.setSubject(emailRequest.getSubject());
        simpleMailMessage.setText(emailRequest.getText());

        javaMailSender.send(simpleMailMessage);

        logger.info("Email sent successfully to {}", emailRequest.getTo());
    }
}
