package com.EmailSendingMicroService.EmailSendingMicroService.services;

import com.EmailSendingMicroService.EmailSendingMicroService.dtos.EmailRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper modelMapper;


    public void sendSimpleEmail(EmailRequest emailRequest) {
        logger.info("Preparing to send email to {}", emailRequest.getTo());

        SimpleMailMessage mailMessage = modelMapper.map(emailRequest, SimpleMailMessage.class);

        try {
            javaMailSender.send(mailMessage);
            logger.info("Email sent successfully to {}", emailRequest.getTo());
        } catch (Exception e) {
            logger.error("Failed to send email to {}", emailRequest.getTo(), e);
            throw new EmailSendingException("Failed to send email to " + emailRequest.getTo(), e);
        }
    }

    public class EmailSendingException extends RuntimeException {
        public EmailSendingException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
