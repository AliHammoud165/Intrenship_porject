package com.EmailSendingMicroService.EmailSendingMicroService.controllers;

import com.EmailSendingMicroService.EmailSendingMicroService.dtos.EmailRequest;
import com.EmailSendingMicroService.EmailSendingMicroService.services.EmailSendingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {
    private final EmailSendingService emailSendingService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
        try {
            emailSendingService.sendSimpleEmail(emailRequest);
            return ResponseEntity.ok("Email sent successfully to " + emailRequest.getTo());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send email: " + e.getMessage());
        }
    }}