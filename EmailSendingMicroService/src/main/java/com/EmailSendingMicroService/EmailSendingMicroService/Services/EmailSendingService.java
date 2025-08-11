package com.EmailSendingMicroService.EmailSendingMicroService.Services;

import com.EmailSendingMicroService.EmailSendingMicroService.DTOs.EmailRequest;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Service
@NoArgsConstructor
public class EmailSendingService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendSimpleEmail(EmailRequest emailRequest) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(emailRequest.getTo());
        simpleMailMessage.setSubject(emailRequest.getSubject());
        simpleMailMessage.setText(emailRequest.getText());
        javaMailSender.send(simpleMailMessage);
    }
}

